package com.example.onlineschool20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth?=null
    private var user_email_login: EditText?=null
    private var user_password_login:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var loginbtn = findViewById<Button>(R.id.login_btn_login)
        var signup_btn_login = findViewById<Button>(R.id.signup_btn_login)
        user_email_login = findViewById(R.id.user_email_login)
        user_password_login = findViewById(R.id.user_passwd_login)
        firebaseAuth= FirebaseAuth.getInstance()
        loginbtn!!.setOnClickListener {
            Login()

        }

        signup_btn_login?.setOnClickListener {
            startActivity(Intent(this@MainActivity ,Signup::class.java))
        }


    }

    private fun Login() {

        var email=user_email_login?.text.toString().trim()
        var passwd =user_password_login?.text.toString()


        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(applicationContext,"email is empty", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(passwd))
        {
            Toast.makeText(applicationContext,"password is empty", Toast.LENGTH_SHORT).show()
        }
        else
        {
            firebaseAuth!!.signInWithEmailAndPassword(email,passwd)?.addOnCompleteListener (object : OnCompleteListener<AuthResult>{



                override fun onComplete(task: Task<AuthResult>) {                               //this is to allow login if email has been verified
                    if (task.isSuccessful) {
                        val user:FirebaseUser =firebaseAuth?.currentUser!!

                        if (user.isEmailVerified)														//	this should be in login activity
                        {
                            startActivity(Intent(this@MainActivity ,Navigation_drawer_main::class.java))
                        }
                    else{
                        val error=task.exception?.message
                        Toast.makeText(applicationContext,"error "+error,Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        val error=task.exception?.message
                        Toast.makeText(applicationContext,"error "+error,Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }




    }
}
