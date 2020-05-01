package com.example.onlineschool20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceActivity
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text

class Navigation_drawer_main : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? =null
    private var firebasedatabase_name : DatabaseReference? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer_main)
        val assignment_fragment=Assignment()
        val profile_fragment=My_profile()
        val setting_fragment =Settings()
       var  toolbar=findViewById<Toolbar>(R.id.toolbar)
        var navigation_view=findViewById<NavigationView>(R.id.navigationmain)
        var drawerlayout=findViewById<DrawerLayout>(R.id.drawer)
        var framelayout_container=findViewById<FrameLayout>(R.id.frame_container)

        var actionBarDrawerToggle=ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close)  //this is to add busrger like sign on top of toolbar
        drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled
        actionBarDrawerToggle.syncState()

        val fragemntchanger = supportFragmentManager.beginTransaction()
        fragemntchanger.replace(R.id.frame_container ,assignment_fragment)                          //this to make sure first screen is of assignment fragment
        fragemntchanger.commit()


  navigation_view.setNavigationItemSelectedListener { item ->                              //this is for navigation view
      when (item.itemId) {
          R.id.item_assignment -> {
              val fragemntchanger = supportFragmentManager.beginTransaction()
              fragemntchanger.replace(R.id.frame_container ,assignment_fragment)
              fragemntchanger.commit()


              true
          }
          R.id.item_myprofile -> {
              val fragmentchanger = supportFragmentManager.beginTransaction()
              fragmentchanger.replace(R.id.frame_container ,profile_fragment)
              fragmentchanger.commit()

              true

          }
          R.id.item_setting -> {
              val fragmentchanger = supportFragmentManager.beginTransaction()
              fragmentchanger.replace(R.id.frame_container ,setting_fragment)
              fragmentchanger.commit()

              true

          }
          else -> false
      }

  }
        var nav_Header=navigation_view.inflateHeaderView(R.layout.nav_header)
        var nav_header_name=nav_Header.findViewById<TextView>(R.id.textview_name_nav)
        var nav_header_email =nav_Header.findViewById<TextView>(R.id.textView_email_nav)                   //addding user information to header in navigation
        var nav_header_class =nav_Header.findViewById<TextView>(R.id.textView_name_class)
       firebaseAuth=FirebaseAuth.getInstance()
        firebasedatabase_name= FirebaseDatabase.getInstance().reference.child("Students Information").child(firebaseAuth?.currentUser!!.uid)


        firebasedatabase_name?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0 : DatabaseError){
            }
            override fun onDataChange(task : DataSnapshot){
                if(task.exists()){
                    val firstname =task.child("firstname").value as String
                    val lastname =task.child("lastname").value as String
                    val phone_no =task.child("email").value as String
                    val Class=task.child("class").value as String
                    nav_header_class.text=Class
                    nav_header_name.text=firstname+" "+lastname
                    nav_header_email.text =phone_no


                }
            }}
        )

    }

}
