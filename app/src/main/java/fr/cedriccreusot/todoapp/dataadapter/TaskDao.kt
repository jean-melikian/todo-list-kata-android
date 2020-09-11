package fr.cedriccreusot.todoapp.dataadapter

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTask(task: TaskEntity)

    @Query("UPDATE task SET is_done = :isDone WHERE id = :id")
    fun updateTask(id: Int, isDone: Boolean)

    @Delete
    fun deleteTask(task: TaskEntity)
}

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean
)
