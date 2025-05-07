package it.mulders.talks.trainingadmin.students

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import java.util.UUID
import kotlin.test.Test

class StudentIdGeneratingCallbackTest {
    private val callback = StudentIdGeneratingCallback()

    @Test
    fun `it should generate a valid StudentId`() {
        val student = Student(null, 1_000, "Susan", "Tudent", "s.tudent@whatever.business")

        val result = callback.onBeforeConvert(student)

        assertThat(result.id).isNotNull()
    }

    @Test
    fun `it should not override an existing StudentId`() {
        val student = Student(UUID.randomUUID(), 1_000, "Susan", "Tudent", "s.tudent@whatever.business")

        val result = callback.onBeforeConvert(student)

        assertThat(result.id).isEqualTo(student.id)
    }
}