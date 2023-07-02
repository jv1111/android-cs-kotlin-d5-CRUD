package com.example.d5_kotlin_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.d5_kotlin_crud.db.Student
import com.example.d5_kotlin_crud.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText;
    private lateinit var etEmail: EditText;
    private lateinit var btnSave: Button;
    private lateinit var btnClear: Button;

    private lateinit var viewModel: StudentViewModel
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnSave = findViewById(R.id.btnSave)
        btnClear = findViewById(R.id.btnClear)
        studentRecyclerView = findViewById(R.id.rvStudents)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        initRecyclerView()

        btnSave.setOnClickListener{
            saveStudentData()
            clearInput()
        }
        btnClear.setOnClickListener{
            clearInput()
        }
    }

    private fun saveStudentData(){
//        val name = etName.text.toString()
//        val email = etEmail.text.toString()
//        val student = Student(0, name, email)
//        viewModel.insertStudent(student)

        viewModel.insertStudent(
            Student(
                0,
                etName.text.toString(),
                etEmail.text.toString()
            )
        )
    }

    private fun clearInput(){
        etName.setText("")
        etEmail.setText("")
    }

    private fun initRecyclerView(){
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter{
            selectedItem: Student -> listItemClicked(selectedItem)
        }
        studentRecyclerView.adapter = adapter

        displayStudentsList()
    }

    private fun displayStudentsList(){
        viewModel.students.observe(this, {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(student: Student){
        Toast.makeText(this, "name: ${student.name}", Toast.LENGTH_LONG).show()

    }
}