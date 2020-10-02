package fr.cedriccreusot.todoapp.dataadapter

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.gesture.GestureOverlayView
import android.view.*
import androidx.core.view.GestureDetectorCompat
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

private class ItemTaskViewHolder(itemView: View, private val onToggle: OnToggle) :
    RecyclerView.ViewHolder(itemView) {
    fun onBind(task: Task) {
        with(itemView) {
            val animator = ObjectAnimator
                .ofFloat(taskTitleCheckBox, "translationX", measuredWidth.toFloat()).apply {
                    repeatCount = 0
                    repeatMode = ValueAnimator.REVERSE
                }
            val detector = GestureDetectorCompat(itemView.context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onFling(
                        e1: MotionEvent?,
                        e2: MotionEvent?,
                        velocityX: Float,
                        velocityY: Float
                    ): Boolean {
                        val result = super.onFling(e1, e2, velocityX, velocityY)
                        animator.start()
                        return result
                    }
                })
            gestureOverlay.addOnGestureListener(object : GestureOverlayView.OnGestureListener {
                override fun onGestureStarted(p0: GestureOverlayView?, p1: MotionEvent?) = Unit

                override fun onGesture(p0: GestureOverlayView?, p1: MotionEvent?) {
                    detector.onTouchEvent(p1)
                }

                override fun onGestureEnded(p0: GestureOverlayView?, p1: MotionEvent?) = Unit

                override fun onGestureCancelled(p0: GestureOverlayView?, p1: MotionEvent?) = Unit
            })

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

class TaskListAdapter(private val onToggle: OnToggle) :
    ListAdapter<Task, RecyclerView.ViewHolder>(diffCallback) {
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
