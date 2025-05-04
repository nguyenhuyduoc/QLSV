package com.example.qlsv
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val myList = mutableListOf<StudentModel>()
    private val REQUEST_CODE_ADD = 100
    private val REQUEST_CODE_EDIT = 101

    private lateinit var addStudentLauncher: ActivityResultLauncher<Intent>
    private lateinit var updateStudentLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)
        val adapter = StudentAdapter(this, R.layout.student_item, myList)
        listView.adapter = adapter

        registerForContextMenu(listView)
        // Đăng ký launcher cho thêm sinh viên
        addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra("name")
                val mssv = it.data?.getStringExtra("mssv")
                val email = it.data?.getStringExtra("email")
                val phone = it.data?.getStringExtra("phone")
                if (name != null && mssv != null) {
                    myList.add(StudentModel(name, mssv, "", ""))
                }
                adapter.notifyDataSetChanged()
            }
        }

        // Đăng ký launcher cho cập nhật sinh viên
        updateStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra("name")
                val mssv = it.data?.getStringExtra("mssv")
                val email = it.data?.getStringExtra("email")
                val phone = it.data?.getStringExtra("phone")
                val position = it.data?.getIntExtra("position", -1) ?: -1
                if ( position >= 0) {
                    val newStudent = StudentModel(name ?: "", mssv ?: "", email ?: "", phone ?: "")
                    myList[position] = newStudent
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
//            val name = data?.getStringExtra("name")
//            val mssv = data?.getStringExtra("mssv")
//            val email = data?.getStringExtra("email")
//            val phone = data?.getStringExtra("phone")
//
//            if (name != null && mssv != null) {
//                myList.add(StudentModel(name, mssv, "", ""))
//                (listView.adapter as StudentAdapter).notifyDataSetChanged()
//            }
//        }
//
//        // Xử lý dữ liệu trả về từ EditStudentActivity
//        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
//            val updatedName = data?.getStringExtra("name")
//            val updatedMssv = data?.getStringExtra("mssv")
//            val position = data?.getIntExtra("position", -1)
//
//            if (updatedName != null && updatedMssv != null && position != null && position >= 0) {
//                // Cập nhật đối tượng trong myList tại vị trí đã chỉnh sửa
//                val student = myList[position]
//                student.name = updatedName
//                student.mssv = updatedMssv
//
//                // Thông báo cập nhật cho Adapter
//                (listView.adapter as StudentAdapter).notifyDataSetChanged()
//            }
//        }
//    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        return when (item.itemId) {
            R.id.menu_update -> {
                val student = myList[position]
                val intent = Intent(this, UpdateStudentActivity::class.java)
                intent.putExtra("name", student.name)
                intent.putExtra("mssv", student.mssv)
                intent.putExtra("position", position)
                intent.putExtra("email", student.email)
                intent.putExtra("phone", student.phone)
                updateStudentLauncher.launch(intent)
                true
            }
            R.id.menu_delete -> {
                myList.removeAt(position)
                (listView.adapter as StudentAdapter).notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}