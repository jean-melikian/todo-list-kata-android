package fr.cedriccreusot.domain

import java.util.*

data class Task(
    val id: UUID = UUID.randomUUID(),
    val description: String? = null,
    val isDone: Boolean = false
) {

}
