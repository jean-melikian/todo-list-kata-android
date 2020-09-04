package fr.cedriccreusot.domain

import fr.cedriccreusot.domain.Task

interface TaskRepository {
    fun getTasks(): List<Task>
}

