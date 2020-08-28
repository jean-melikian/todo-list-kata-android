package fr.cedriccreusot.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.cedriccreusot.domain.Task

class TaskListViewModel : ViewModel() {
    private val taskList: MutableLiveData<List<Task>> = MutableLiveData()

    init {
        taskList.postValue(
            listOf(
                Task(description = "Faire des crepes"),
                Task(description = "Manger de la salade"),
                Task(description = "Jouer a la roulette russe"),
                Task(description = "Jouer a la roulette russe si toujours vivant")
            )
        )
    }

    fun taskList(): LiveData<List<Task>> = taskList

    fun addItem(description: String) {
        taskList.postValue(taskList.value?.plus(listOf(Task(description = description))))
    }
}
