package it.mulders.talks.trainingadmin.students

class StudentNotFoundException(studentId: Long) : Exception(createMessage(studentId)) {
    companion object {
        private fun createMessage(studentId: Long?): String = studentId
            ?.let { "Student '$studentId' was not found." }
            ?: "Student was not found."
    }
}