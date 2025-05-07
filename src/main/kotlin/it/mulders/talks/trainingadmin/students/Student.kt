package it.mulders.talks.trainingadmin.students

import it.mulders.talks.trainingadmin.courses.Course
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Persistent
import java.util.UUID

@Persistent
data class Student(
    @field:Id val id: UUID?,
    val studentId: Long?,
    @field:NotNull @field:NotBlank val firstName: String,
    @field:NotNull @field:NotBlank val lastName: String,
    @field:NotNull @field:NotBlank val email: String,
) {
    @PersistenceCreator
    constructor(id: UUID?) : this(id, 0, "", "", "")

    constructor() : this(null)
}