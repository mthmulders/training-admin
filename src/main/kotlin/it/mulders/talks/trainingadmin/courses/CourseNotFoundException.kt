package it.mulders.talks.trainingadmin.courses

class CourseNotFoundException(courseId: String?) : Exception(createMessage(courseId)) {
    companion object {
        private fun createMessage(courseId: String?): String = courseId
            ?.let { "Course '$courseId' was not found." }
            ?: "Course was not found."
    }
}
