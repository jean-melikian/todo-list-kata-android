package fr.cedriccreusot.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.*


class TaskTest {
    @Test
    fun `GIVEN an empty todo list WHEN adding a task THEN todo list should have 1 item`() {
        val todoList = mutableListOf<Task>()

        todoList.add(Task())

        assertEquals(1, todoList.size)
    }

    @Test
    fun `GIVEN an filled todo list WHEN removing a task THEN todo list should have 1 item removed`() {
        val uuid = UUID.randomUUID()
        val todoList = mutableListOf(Task(), Task(id = uuid), Task())

        todoList.removeIf {
            it.id == uuid
        }
        assertEquals(2, todoList.size)
        assertNull(todoList.find { it.id == uuid })
    }

    @Test
    fun `GIVEN filled todo list with only undone tasks WHEN completing a task THEN should have 1 task done`() {
        TODO("YOU SHOULD IMPLEMENT IT")
    }
}
