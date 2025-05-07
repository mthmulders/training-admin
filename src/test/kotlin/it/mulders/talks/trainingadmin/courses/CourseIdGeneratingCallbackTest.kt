package it.mulders.talks.trainingadmin.courses

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import java.util.UUID
import kotlin.test.Test

class CourseIdGeneratingCallbackTest {
    private val callback = CourseIdGeneratingCallback()

    @Test
    fun `it should generate a valid CourseId`() {
        val course = Course(null, "TEST", "Test course", emptySet())

        val result = callback.onBeforeConvert(course)

        assertThat(result.id).isNotNull()
    }

    @Test
    fun `it should not override an existing CourseId`() {
        val course = Course(UUID.randomUUID(), "TEST", "Test course", emptySet())

        val result = callback.onBeforeConvert(course)

        assertThat(result.id).isEqualTo(course.id)
    }
}