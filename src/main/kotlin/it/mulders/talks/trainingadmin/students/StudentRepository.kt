package it.mulders.talks.trainingadmin.students

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface StudentRepository : CrudRepository<Student, UUID>, PagingAndSortingRepository<Student, UUID> {
    fun findByStudentId(studentId: Long): Student?
    fun findByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(firstName: String, lastName: String): Collection<Student>
}