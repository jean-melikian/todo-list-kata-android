package fr.cedriccreusot.todoapp.dataadapter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.cedriccreusot.todoapp.ui.MainActivity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        fun create(applicationContext: Context): TaskDatabase = Room.databaseBuilder(applicationContext, TaskDatabase::class.java,
            MainActivity.DATA_STORE_TASKS
        ).build()
    }
}