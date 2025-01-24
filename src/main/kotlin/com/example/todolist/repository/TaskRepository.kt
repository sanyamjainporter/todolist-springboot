package com.example.todolist.repository

import com.example.todolist.models.Task
import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository <Task, Long> {
}