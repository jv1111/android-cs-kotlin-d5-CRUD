package com.example.d5_kotlin_crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.d5_kotlin_crud.db.Student
import com.example.d5_kotlin_crud.db.StudentDao
import kotlinx.coroutines.launch

class StudentViewModel(private val dao : StudentDao):ViewModel() {
    val students = dao.getAllStudents()//get the students

    fun insertStudent(student: Student)=viewModelScope.launch{
        dao.insertStudent(student)
    }

    fun updateStudent(student: Student)=viewModelScope.launch{
        dao.updateStudent(student)
    }

    fun deleteStudent(student: Student)=viewModelScope.launch{
        dao.deleteStudent(student)
    }

}