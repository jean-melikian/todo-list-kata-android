package fr.cedriccreusot.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    fun add(description: String)
    fun updateTask(id: Int, isDone: Boolean)
}

