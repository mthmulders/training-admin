package it.mulders.talks.trainingadmin.courses

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface CourseRepository : CrudRepository<Course, UUID>, PagingAndSortingRepository<Course, UUID> {
    fun findByCourseId(courseId: String): Course?
}