package it.mulders.talks.trainingadmin.courses

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Persistent
import org.springframework.data.annotation.PersistenceCreator
import java.util.UUID

@Persistent
data class Course(
    @field:Id  val id: UUID?,
    @field:NotNull @field:NotBlank val courseId: String,
    @field:NotNull @field:NotBlank val name: String,
    @field:NotNull val duration: Int = 0
) {
    @PersistenceCreator
    constructor(id: UUID?) : this(id, "", "", 0)

    constructor() : this(null)
}
