package com.example.qlsv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        val name = intent.getStringExtra("name")
        val mssv = intent.getStringExtra("mssv")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val position = intent.getIntExtra("position", -1)

        // Đảm bảo bạn dùng đúng ID cho các EditText
        findViewById<EditText>(R.id.editTextName).setText(name)
        findViewById<EditText>(R.id.editTextStudentId).setText(mssv)

        findViewById<Button>(R.id.buttonUpdate).setOnClickListener {
            val updatedName = findViewById<EditText>(R.id.editTextName).text.toString()
            val updatedMssv = findViewById<EditText>(R.id.editTextStudentId).text.toString()

            if (updatedName.isNotEmpty() && updatedMssv.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", updatedName)
                resultIntent.putExtra("mssv", updatedMssv)
                resultIntent.putExtra("email", email)
                resultIntent.putExtra("phone", phone)
                resultIntent.putExtra("position", intent.getIntExtra("position", -1))  // Trả lại vị trí
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}