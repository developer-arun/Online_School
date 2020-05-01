package com.example.onlineschool20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class My_profile : Fragment() {

    private var firebaseAuth: FirebaseAuth? =null
    private var firebasedatabase_name : DatabaseReference? =null


    override fun onCreateView(
        inflater: LayoutInflater ,container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        var layoutInflater=  inflater.inflate(R.layout.fragment_my_profile ,container ,false)
            var editMy_profile_btn=layoutInflater.findViewById<Button>(R.id.edit_btn_profile)
            var profile_img_btn=layoutInflater.findViewById<ImageButton>(R.id.student_profile_img_btn)
        var firstName=layoutInflater.findViewById<TextView>(R.id.textView_student_firstname)
        var lastName=layoutInflater.findViewById<TextView>(R.id.textview_student_lastname_profile)
        var email_id=layoutInflater.findViewById<TextView>(R.id.textview_student_phone_profile)
        var Class=layoutInflater.findViewById<TextView>(R.id.textView_student_class_profile)



        firebaseAuth=FirebaseAuth.getInstance()
        firebasedatabase_name= FirebaseDatabase.getInstance().reference.child("Students Information").child(firebaseAuth?.currentUser!!.uid)

        firebasedatabase_name?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0 : DatabaseError){
            }
            override fun onDataChange(task : DataSnapshot){
                if(task.exists()){
                    val firstname =task.child("firstname").value as String
                    val lastname =task.child("lastname").value as String
                    val emailid =task.child("email").value as String
                    val CLass =task.child("class").value as String
                    Class?.text=CLass
                    email_id?.text =emailid
                    firstName?.text =firstname
                    lastName?.text =lastname


                }
            }}
        )
        editMy_profile_btn.setOnClickListener {
            val fragmentchanger=parentFragmentManager.beginTransaction()
            fragmentchanger.replace(R.id.frame_container,Update_profile()).commit()
        }




        return layoutInflater
    }



}
