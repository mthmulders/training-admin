package it.mulders.talks.trainingadmin.courses

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface CourseRepository : CrudRepository<Course, UUID>, PagingAndSortingRepository<Course, UUID> {
    fun findByCourseId(courseId: String): Course?

    @Query("select id, course_id from course where id in (:ids)")
    fun findCourseIdsByIdIn(ids: Collection<UUID>): Collection<IdAndCourseId>

    data class IdAndCourseId (
        val id: UUID,
        val courseId: String,
    )
}