package fr.cedriccreusot.domain

data class Task(
    val id: Int = 0,
    val description: String? = null,
    val isDone: Boolean = false
) {
    fun toggleIsDone(): Task = this.copy(isDone = !this.isDone)
}
