package com.example.onlineschool20

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Update_profile : Fragment() {

    private var firebaseAuth:FirebaseAuth? =null
    private var firebasedatabase_name : DatabaseReference? =null
    //private var firebasedatabase:DatabaseReference?=null

    override fun onCreateView(
        inflater: LayoutInflater ,container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var layoutInflater=inflater.inflate(R.layout.fragment_update_profile ,container ,false)

         firebaseAuth=FirebaseAuth.getInstance()
      //  firebasedatabase= FirebaseDatabase.getInstance().reference.child("Students Information").child(firebaseAuth?.currentUser!!.uid)
        firebasedatabase_name=FirebaseDatabase.getInstance().reference.child("Students Information").child(firebaseAuth?.currentUser!!.uid)





        var firstName=layoutInflater.findViewById<EditText>(R.id.edittext_student_firstname)
        var lastName=layoutInflater.findViewById<EditText>(R.id.edittext_student_name_update_profile)
        var email_id =layoutInflater.findViewById<EditText>(R.id.edittext_student_phone_profile_update)
        var save_btn=layoutInflater.findViewById<Button>(R.id.btn_save_upload_profile)
        var Class_code =layoutInflater.findViewById<EditText>(R.id.editText_class_update)


       /* firebasedatabase?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0 : DatabaseError){
            }
            override fun onDataChange(task : DataSnapshot){                                             to add current users  data to the edittext
                if(task.exists()){
                    firstName.setText(task.child("firtname").value as String)
                    lastName.setText(task.child("lastname").value as String)
                    Class_code.setText(task.child("class").value as String)
                    email_id.setText(task.child("email").value as String)

                }
            }}
        )*/

        save_btn.setOnClickListener {


            var firstname=firstName?.text.toString().trim()
            var lastname=lastName?.text.toString().trim()
            var emailid=email_id?.text.toString().trim()

            var class_code=Class_code?.text.toString()


            if(TextUtils.isEmpty(firstname)||TextUtils.isEmpty(lastname))
            {
                Toast.makeText(context,"you have forgot to fill your name",Toast.LENGTH_SHORT).show()
            }

            else if(TextUtils.isEmpty(emailid))
            {
                Toast.makeText(context,"you have forgot to fill your phone",Toast.LENGTH_SHORT).show()
            }


            else if(TextUtils.isEmpty(class_code))
            {
                Toast.makeText(context,"you have forgot to fill your class",Toast.LENGTH_SHORT).show()
            }
            else
            {

                val userinfo =HashMap<String,Any>()
                userinfo.put("firstname",firstname)
                userinfo.put("lastname",lastname)
                userinfo.put("email",emailid)
                userinfo.put("class",class_code)


                firebasedatabase_name
                    ?.updateChildren(userinfo)?.addOnCompleteListener(object : OnCompleteListener<Void>
                {


                    override fun onComplete(task: Task<Void>) {
                        if (task.isSuccessful)
                        {
                            Toast.makeText(context,"your profile is updated",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context,"Error "+task.exception?.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                )
            }

            val fragmentchanger =parentFragmentManager.beginTransaction()
            fragmentchanger.replace(R.id.frame_container ,My_profile())
            fragmentchanger.commit()


        }





        return layoutInflater
    }



    }

