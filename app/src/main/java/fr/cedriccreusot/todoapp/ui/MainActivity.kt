package fr.cedriccreusot.todoapp.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import fr.cedriccreusot.domain.TaskRepository
import fr.cedriccreusot.todoapp.R
import fr.cedriccreusot.todoapp.dataadapter.TaskDatabase
import fr.cedriccreusot.todoapp.dataadapter.TaskRepositoryImpl
import fr.cedriccreusot.todoapp.dataadapter.TaskListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskRepository: TaskRepository
    private lateinit var taskListViewModel: TaskListViewModel
    private var taskListAdapter: TaskListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskDatabase = TaskDatabase.create(applicationContext)
        taskRepository = TaskRepositoryImpl(taskDatabase.getTaskDao())
        taskListViewModel = TaskListViewModel(taskRepository)
        taskListAdapter = TaskListAdapter(taskListViewModel::setTaskIsDone)

        tasksRecyclerView.adapter = taskListAdapter
        taskListViewModel.taskList.observe(this, Observer { tasks ->
            taskListAdapter?.submitList(tasks)
        })

        addItemDescriptionEditText.setOnEditorActionListener { editText, action, keyEvent ->
            if (EditorInfo.IME_ACTION_DONE == action) {
                taskListViewModel.addItem(editText.text.toString())
                editText.text = ""
                true
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        taskListAdapter = null
    }

    companion object {
        const val DATA_STORE_TASKS = "tasks"
    }
}
