package fr.cedriccreusot.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.cedriccreusot.domain.Task

class TaskListViewModel: ViewModel() {
    private val taskList: MutableLiveData<List<Task>> = MutableLiveData()
    
    fun taskList(): LiveData<List<Task>> = taskList
}