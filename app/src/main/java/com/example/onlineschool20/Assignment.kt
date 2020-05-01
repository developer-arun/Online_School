package com.example.onlineschool20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Assignment : Fragment() {
    private var firebaseStorage: StorageReference?=null
    private var FDteachersemail: DatabaseReference?=null
    private var firebaseAuth:FirebaseAuth?=null
    private var  FDstudentsinformation:DatabaseReference?=null

    override fun onCreateView(
        inflater: LayoutInflater ,container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater= inflater.inflate(R.layout.fragment_assignment ,container ,false)
        firebaseAuth= FirebaseAuth.getInstance()
        FDteachersemail=FirebaseDatabase.getInstance().reference
        FDstudentsinformation=FirebaseDatabase.getInstance().reference.child("Students Information").child(firebaseAuth?.currentUser!!.uid).child("email")
        var imgbtn_add_assignment=layoutInflater.findViewById<ImageButton>(R.id.imgbtn_add_assignment)
        imgbtn_add_assignment.setOnClickListener{
            var framechanger=parentFragmentManager.beginTransaction()
            framechanger.replace(R.id.frame_container,Add_assignmnet()).commit()
        }

        if (FDstudentsinformation!= FDteachersemail!!.child("teacher email")) {
            imgbtn_add_assignment.isInvisible

        }
        else
        {
            imgbtn_add_assignment.isVisible
        }










        return layoutInflater
    }

}
