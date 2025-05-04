package com.example.qlsv

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val context: Activity,
    private val layoutId: Int,
    private val myList: MutableList<StudentModel>
): ArrayAdapter<StudentModel>(context, layoutId, myList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutId, parent, false)

        val student = myList[position]

        val nameView = view.findViewById<TextView>(R.id.textViewName)
        val mssvView = view.findViewById<TextView>(R.id.textViewStudentId)

        nameView.text = student.name
        mssvView.text = student.mssv

        return view
    }
}


