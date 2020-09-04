package fr.cedriccreusot.todoapp.dataadapter

import android.content.SharedPreferences
import androidx.room.RoomDatabase
import fr.cedriccreusot.domain.Task
import fr.cedriccreusot.domain.TaskRepository

class TaskRepositoryImpl(private val dataStore: TaskDao) : TaskRepository {
    override fun getTasks(): List<Task> = emptyList()
}