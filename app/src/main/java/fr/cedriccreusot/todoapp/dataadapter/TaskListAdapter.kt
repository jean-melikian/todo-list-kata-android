package fr.cedriccreusot.todoapp.dataadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.cedriccreusot.domain.Task
import fr.cedriccreusot.todoapp.R
import kotlinx.android.synthetic.main.item_task.view.*

typealias OnToggle = (isChecked: Boolean, id: Int) -> Unit

private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}

private class ItemTaskViewHolder(itemView: View, private val onToggle: OnToggle) : RecyclerView.ViewHolder(itemView) {
    fun onBind(task: Task) {
        with(itemView) {
            taskTitleCheckBox.apply {
                text = task.description
                setOnCheckedChangeListener(null)
                isChecked = task.isDone
                setOnCheckedChangeListener { _, isChecked ->
                    onToggle(isChecked, task.id)
                }
            }
        }
    }
}

class TaskListAdapter(private val onToggle: OnToggle) : ListAdapter<Task, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemTaskViewHolder(layoutInflater.inflate(viewType, parent, false), onToggle)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemTaskViewHolder).onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_task
    }
}