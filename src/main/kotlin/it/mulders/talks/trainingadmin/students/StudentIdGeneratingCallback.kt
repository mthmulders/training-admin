package it.mulders.talks.trainingadmin.students

import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StudentIdGeneratingCallback : BeforeConvertCallback<Student> {
    override fun onBeforeConvert(student: Student): Student {
        return student.id
            ?. let { student }
            ?: student.copy(id = UUID.randomUUID())
    }
}