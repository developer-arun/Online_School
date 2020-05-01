package com.example.onlineschool20

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*
import kotlin.collections.HashMap

class Add_assignmnet : Fragment() {

    private var FDstorage: StorageReference?=null
    private var FDassignment_url:DatabaseReference?=null
    private var FDstudentprofile:DatabaseReference?=null
    private var FDassignmentDesc:DatabaseReference?=null
    private var firebaseAuth:FirebaseAuth?=null
    var fileUri:Uri?=null
    private var filename:String?=null
    private var Progressdialog:ProgressDialog?=null


    override fun onCreateView(
        inflater: LayoutInflater ,container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var selected_class:String?=null
        var class_student:String?=null

        var layoutInflater=inflater.inflate(R.layout.fragment_add_assignmnet ,container ,false)
        var selectclass=layoutInflater.findViewById<Spinner>(R.id.spinner_select_class)
        var arrayAdapter=
            activity?.let { ArrayAdapter.createFromResource(it ,R.array.select_class,android.R.layout.simple_spinner_item) }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectclass.setAdapter(arrayAdapter)
        selectclass.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context,"Select class First",Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>? ,
                view: View? ,
                position: Int ,
                id: Long
            ) {

                 selected_class = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity,selected_class, Toast.LENGTH_SHORT).show()
            }
        }
        var time=Calendar.getInstance().time.toString()
        class_student=selected_class.toString()
        firebaseAuth=FirebaseAuth.getInstance()
        FDstudentprofile=FirebaseDatabase.getInstance().reference.child("Students Information").child(firebaseAuth?.currentUser!!.uid)
        FDassignment_url= FirebaseDatabase.getInstance().reference.child("Assignment_Url").child(class_student)
        FDstorage= FirebaseStorage.getInstance().reference.child("Assignments_file").child(class_student)
        FDassignmentDesc=FirebaseDatabase.getInstance().reference.child("AssignmentDesc").child(time+"_"+FDstudentprofile?.child("firstname"))
        var desc_asssignment=layoutInflater.findViewById<EditText>(R.id.editText_assignment_desc_addass)
        var title_assignment=layoutInflater.findViewById<EditText>(R.id.editText_assignment_title)
        val assignment_desc_info=HashMap<String,Any>()
        assignment_desc_info.put("Description Assignment",desc_asssignment.text.toString())
        assignment_desc_info.put("Title description",title_assignment.text.toString())
        assignment_desc_info.put("Faculty name",FDstudentprofile?.child("firstname"+"lastname").toString())
        assignment_desc_info.put("class",class_student)
        assignment_desc_info.put("uploaded time",time)
        assignment_desc_info.put("filename",filename.toString())


        var btnupload_assignment=layoutInflater.findViewById<Button>(R.id.button_upload_assignment)
        var text_select_file=layoutInflater.findViewById<TextView>(R.id.textview_select_file)
        btnupload_assignment.setOnClickListener {
            if (fileUri!=null)
                upload_file_firebase(fileUri!!)
            else
                Toast.makeText(context,"please select a file",Toast.LENGTH_SHORT).show()
            FDassignmentDesc?.updateChildren(assignment_desc_info)?.addOnCompleteListener(object : OnCompleteListener<Void>{

                override fun onComplete(p0: Task<Void>) {

                }
            })

        }
        text_select_file.setOnClickListener{
            Selectfile()
            text_select_file.text=filename

        }





        return layoutInflater


    }

    private fun upload_file_firebase(fileUri: Uri) {

       FDstorage?.putFile(fileUri)?.addOnSuccessListener(object :OnSuccessListener<UploadTask.TaskSnapshot>
       {
           override fun onSuccess(uploadTask: UploadTask.TaskSnapshot?) {
              val url_file=uploadTask!!.metadata!!.reference!!.downloadUrl.toString()

                FDassignment_url?.setValue(url_file)?.addOnCompleteListener( object : OnCompleteListener<Void>
                {
                    override fun onComplete(task: Task<Void>) {
                        if (task.isSuccessful)
                        {
                            Toast.makeText(context,"FIle has been uploaded",Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(context,"Error  !! "+task.exception?.message.toString() ,Toast.LENGTH_SHORT).show()

                    }
                })


           }
       })
           ?.addOnProgressListener(object :OnProgressListener<UploadTask.TaskSnapshot>
           {
               override fun onProgress(p0: UploadTask.TaskSnapshot) {
                   Progressdialog?.setTitle("Uploading")
                   Progressdialog?.show()
                   val progress: Double =
                       100.0 * p0.getBytesTransferred() / p0.getTotalByteCount()

                   //displaying percentage in progress dialog

                   //displaying percentage in progress dialog
                   Progressdialog?.setMessage("Uploaded " + progress.toInt() + "%...")



               }
           })



       }


    private fun Selectfile()
    {
        val file= Intent()
        file.type="*/*"
        file.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(file,441)

    }

    override fun onActivityResult(requestCode: Int ,resultCode: Int ,data: Intent?) {
        super.onActivityResult(requestCode ,resultCode ,data)
        if(requestCode==441 && resultCode==Activity.RESULT_OK && data!=null)
        {
                fileUri=data.data
                filename=fileUri.toString()


        }
        else
        {
            Toast.makeText(context,"please select a file",Toast.LENGTH_SHORT).show()
        }
    }


}

