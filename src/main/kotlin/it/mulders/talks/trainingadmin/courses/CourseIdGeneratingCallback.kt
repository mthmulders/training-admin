package it.mulders.talks.trainingadmin.courses

import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CourseIdGeneratingCallback : BeforeConvertCallback<Course> {
    override fun onBeforeConvert(course: Course): Course {
        return course.id
            ?. let { course }
            ?: course.copy(id = UUID.randomUUID())
    }
}
