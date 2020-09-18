package fr.cedriccreusot.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import fr.cedriccreusot.domain.Task
import fr.cedriccreusot.domain.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val taskList: LiveData<List<Task>> = taskRepository.getTasks().asLiveData()

    fun addItem(description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.add(description)
        }
    }

    fun setTaskIsDone(isDone: Boolean, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(id = id, isDone = isDone)
        }
    }
}
