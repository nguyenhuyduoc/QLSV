package com.example.qlsv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Xử lý thêm sinh viên khi bấm nút "Lưu"
        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val mssv = findViewById<EditText>(R.id.editTextStudentId).text.toString()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
            val phone = findViewById<EditText>(R.id.editTextPhone).text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("mssv", mssv)
                resultIntent.putExtra("email", email)
                resultIntent.putExtra("phone", phone)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}