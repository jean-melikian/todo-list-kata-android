package fr.cedriccreusot.todoapp.dataadapter

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTask(task: TaskEntity)

    @Update
    fun updateTask(task: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)
}

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "is_done") val isDone: Boolean
)