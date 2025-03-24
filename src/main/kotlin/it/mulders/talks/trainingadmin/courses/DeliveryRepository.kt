package it.mulders.talks.trainingadmin.courses

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID

@Component
interface DeliveryRepository : CrudRepository<Delivery, UUID>, PagingAndSortingRepository<Delivery, UUID> {
    fun findByStartDateIsAfter(date: LocalDate, pageable: Pageable): Page<Delivery>
    fun findByCourseIdAndStartDateIsAfter(courseId: UUID, startDate: LocalDate): Collection<Delivery>
}
