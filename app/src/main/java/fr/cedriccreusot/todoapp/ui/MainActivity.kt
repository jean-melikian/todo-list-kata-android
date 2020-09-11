package fr.cedriccreusot.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.domain.Task
import fr.cedriccreusot.domain.TaskRepository
import fr.cedriccreusot.todoapp.R
import fr.cedriccreusot.todoapp.dataadapter.TaskDatabase
import fr.cedriccreusot.todoapp.dataadapter.TaskRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_task.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var taskDatabase: TaskDatabase
    private lateinit var taskRepository: TaskRepository
    private lateinit var taskListViewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskDatabase = TaskDatabase.create(applicationContext)
        taskRepository = TaskRepositoryImpl(taskDatabase.getTaskDao())
        taskListViewModel = TaskListViewModel(taskRepository)

        tasksRecyclerView.adapter = tasksListAdapter
        taskListViewModel.taskList.observe(this, Observer { tasks ->
            tasksListAdapter.submitList(tasks)
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

    companion object {
        const val DATA_STORE_TASKS = "tasks"
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}

private val tasksListAdapter = object : ListAdapter<Task, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemTaskViewHolder(layoutInflater.inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemTaskViewHolder).onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_task
    }
}

private class ItemTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(task: Task) {
        with(itemView) {
            taskTitleCheckBox.apply {
                text = task.description
                isChecked = task.isDone
                setOnCheckedChangeListener { _, isChecked ->

                }
            }
        }
    }
}
