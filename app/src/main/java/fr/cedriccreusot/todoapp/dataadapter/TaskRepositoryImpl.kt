package fr.cedriccreusot.todoapp.dataadapter

import fr.cedriccreusot.domain.Task
import fr.cedriccreusot.domain.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> = taskDao.getAll().distinctUntilChanged().map {
        it.map { entity ->
            Task(
                id = entity.id,
                description = entity.description,
                isDone = entity.isDone
            )
        }
    }

    override fun add(description: String) {
        val task = TaskEntity(
            description = description,
            isDone = false
        )
        taskDao.upsertTask(task)
    }

    override fun updateTask(id: Int, isDone: Boolean) {
        taskDao.updateTask(id, isDone)
    }
}
