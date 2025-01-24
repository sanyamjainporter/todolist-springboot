package com.example.todolist.controller

import com.example.todolist.models.Task
import com.example.todolist.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/tasks")
class TaskController(@Autowired private val taskRepository: TaskRepository) {

    @GetMapping("")
    fun getAllTasks() : List<Task> =
        taskRepository.findAll().toList()

    @PostMapping("")
    fun createTask(@RequestBody task: Task): ResponseEntity<Task> {
        val createdTask = taskRepository.save(task)
        return ResponseEntity(createdTask, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateTaskById(@PathVariable("id") taskId: Long, @RequestBody task: Task): ResponseEntity<Task> {
        val existingTask = taskRepository.findById(taskId).orElse(null) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        // Update fields of the existing task with the new values
        val updatedTask = existingTask.copy(
            title = task.title,
            description = task.description,
            isCompleted = task.isCompleted ?: existingTask.isCompleted,
            deadLine = task.deadLine
        )

        taskRepository.save(updatedTask)

        return ResponseEntity(updatedTask, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteTaskById(@PathVariable("id") taskId: Long) : ResponseEntity<Task> {
        taskRepository.deleteById(taskId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


    @PutMapping("/markFinished/{id}")
    fun markTaskCompletedById(@PathVariable("id") taskId: Long) : ResponseEntity<Task> {
        val existingTask = taskRepository.findById(taskId).orElse(null) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val updatedTask = existingTask.copy(isCompleted = true)
        taskRepository.save(updatedTask)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}