package com.example.onlineschool20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth?=null
    private var user_email_signup:EditText?=null
    private var user_password_signup:EditText?=null
    private var user_name_signup:EditText?=null
    private var user_password_verify_signup:EditText?=null
    private  var user_lastname_signup:EditText?=null
    private var user_class_sigup:EditText?=null
    private var firebasedatabase : DatabaseReference? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var signupbtn=findViewById<Button>(R.id.signup_btn_signup)
        user_name_signup=findViewById(R.id.user_name_signup)
        user_password_signup=findViewById(R.id.user_password_signup)
        user_password_verify_signup=findViewById(R.id.user_password_verify_signup)
        user_lastname_signup=findViewById(R.id.edittext_lastname_signup)
        user_email_signup=findViewById(R.id.user_email_signup)
        user_class_sigup=findViewById(R.id.edittext_class_signup)
        firebaseAuth=FirebaseAuth.getInstance()
        firebasedatabase=FirebaseDatabase.getInstance().reference.child("Students Information")
        signupbtn?.setOnClickListener {
            Register_user()

        }
    }

    private fun Register_user() {


        var user_lastname=user_lastname_signup?.text.toString()
        var user_name=user_name_signup?.text.toString()
        var passwd_verify=user_password_verify_signup?.text.toString()
        var email = user_email_signup?.text.toString().trim()
        var password = user_password_signup?.text.toString()
        var user_class=user_class_sigup?.text.toString()

            if (TextUtils.isEmpty(email))
                Toast.makeText(applicationContext, "email is empty", Toast.LENGTH_SHORT).show()
            else if (TextUtils.isEmpty(user_name))
                Toast.makeText(applicationContext, "firstname is empty", Toast.LENGTH_SHORT).show()
            else if (TextUtils.isEmpty(user_lastname))
                Toast.makeText(applicationContext, "lastname is empty", Toast.LENGTH_SHORT).show()
            else if (TextUtils.isEmpty(passwd_verify))
                Toast.makeText(applicationContext,"password is empty", Toast.LENGTH_SHORT).show()
            else if (TextUtils.isEmpty(password))
                Toast.makeText(applicationContext,"password is empty", Toast.LENGTH_SHORT).show()
            else if (passwd_verify!=password )
                Toast.makeText(applicationContext,"password is too short", Toast.LENGTH_SHORT).show()
            else if (TextUtils.isEmpty(user_class))
            {
                Toast.makeText(applicationContext,"enter class", Toast.LENGTH_SHORT).show()
            }
            else {
                firebaseAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful) {
                                startActivity(Intent(this@Signup,MainActivity::class.java))
                                val user= firebaseAuth?.currentUser!!
                                user.sendEmailVerification()
                                    ?.addOnCompleteListener(object : OnCompleteListener<Void> {

                                        override fun onComplete(task: Task<Void>) {
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "you have been registered Successfully  " ,
                                                    Toast.LENGTH_SHORT
                                                ).show()


                                            } else {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Error:" + task.exception?.message,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        }
                                    })
                                val userinfo =HashMap<String,Any>()
                                userinfo.put("firstname",user_name)
                                userinfo.put("lastname",user_lastname)
                                userinfo.put("email",email)
                                userinfo.put("class",user_class)

                                firebasedatabase?.child(firebaseAuth?.currentUser!!.uid)?.updateChildren(userinfo)!!.addOnCompleteListener( object : OnCompleteListener<Void>{
                                    override fun onComplete(task: Task<Void>) {
                                        if (task.isSuccessful)
                                            Toast.makeText(applicationContext," check your email to verify ",Toast.LENGTH_SHORT).show()
                                        else
                                        {
                                            Toast.makeText(applicationContext,"Error !! "+task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                                        }


                                    }
                                })

                            }
                        }
                    })
            }


    }
}
