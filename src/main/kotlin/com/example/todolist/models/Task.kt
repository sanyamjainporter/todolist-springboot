package com.example.todolist.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val title: String,

    val description: String,

    @Column(name = "is_completed")
    val isCompleted: Boolean? = false,

    @Column(name = "dead_line")
    val deadLine: LocalDateTime,

    @Column(name = "creation_time")
    var creationTime: LocalDateTime? = null,

    @Column(name = "updation_time")
    var updationTime: LocalDateTime? = null
) {
    @PrePersist
    fun onCreate() {
        val now = LocalDateTime.now()
        creationTime = now
        updationTime = now
    }

    @PreUpdate
    fun onUpdate() {
        updationTime = LocalDateTime.now()
    }
}
