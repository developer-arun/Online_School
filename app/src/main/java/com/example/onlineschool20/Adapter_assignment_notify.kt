package com.example.onlineschool20

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

abstract class Adapter_assignment_notify (data:ArrayList<Download_module_assognment> ,internal var context:Context):RecyclerView.Adapter<Adapter_assignment_notify.ViewHolder>()
{


class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    internal var title_assignment:TextView
    internal  var Desc_assignment:TextView
    internal var faculty_name:TextView
    internal var uploadedtime:TextView
    init {
        title_assignment=itemView.findViewById(R.id.textView_assignment_title_notify)
        Desc_assignment=itemView.findViewById(R.id.textView_assignment_desc_notify)
        faculty_name=itemView.findViewById(R.id.textView_faculty_name_notify)
        uploadedtime=itemView.findViewById(R.id.textView_uploaded_time)
    }



}
}