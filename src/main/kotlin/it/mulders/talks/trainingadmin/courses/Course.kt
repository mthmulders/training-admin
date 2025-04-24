package it.mulders.talks.trainingadmin.courses

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Persistent
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.relational.core.mapping.MappedCollection
import java.util.UUID

@Persistent
data class Course private constructor(
    @field:Id  val id: UUID?,
    @field:NotNull @field:NotBlank val courseId: String,
    @field:NotNull @field:NotBlank val name: String,
    @field:NotNull val duration: Int,
    @field:NotNull @field:MappedCollection(idColumn = "COURSE_ID", keyColumn = "ID") private val _deliveries: MutableSet<Delivery> = mutableSetOf(),
) {
    val deliveries: Set<Delivery> get() = _deliveries.toSet()

    @PersistenceCreator
    constructor(id: UUID?) : this(id, "", "", 0, mutableSetOf())

    constructor() : this(null)

    companion object {
        operator fun invoke(id: UUID?, courseId: String, name: String, duration: Int, deliveries: Set<Delivery>): Course = this(id, courseId, name, duration, deliveries)
    }
}
