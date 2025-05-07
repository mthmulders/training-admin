package it.mulders.talks.trainingadmin.courses

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Persistent
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.relational.core.mapping.MappedCollection
import java.util.UUID

@Persistent
data class Course constructor(
    @field:Id  val id: UUID?,
    @field:NotNull @field:NotBlank val courseId: String,
    @field:NotNull @field:NotBlank val name: String,
    @field:NotNull @field:MappedCollection(idColumn = "COURSE_ID", keyColumn = "ID") private val _deliveries: Set<Delivery> = mutableSetOf(),
) {
    val deliveries: Set<Delivery> get() = _deliveries.toSet()

    @PersistenceCreator
    constructor(id: UUID?) : this(id, "", "", mutableSetOf())

    constructor() : this(null)
}
