package it.mulders.talks.trainingadmin.courses

import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Persistent
import java.time.LocalDate
import java.util.UUID

@Persistent
data class Delivery(
    @field:Id val id: UUID?,
    @field:NotNull val startDate: LocalDate,
    @field:NotNull val courseId: UUID,
) {

    @PersistenceCreator
    constructor(id: UUID?, courseId: UUID) : this(id, LocalDate.now(), courseId)

    fun started(): Boolean = LocalDate.now() >= startDate
}
