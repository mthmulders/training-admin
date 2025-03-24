package it.mulders.talks.trainingadmin;

import it.mulders.talks.trainingadmin.courses.CourseRepository
import it.mulders.talks.trainingadmin.courses.DeliveryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate
import java.util.UUID

@Controller
@RequestMapping("/")
class HomeController(
    private val deliveryRepository: DeliveryRepository,
    private val courseRepository: CourseRepository
) {
    private fun retrieveUpcomingDeliveriesWithCourse(pageable: Pageable): Page<DeliveryWithCourseName> {
        val deliveries = deliveryRepository.findByStartDateIsAfter(LocalDate.now().minusDays(1), pageable)
        val courseIds = deliveries.content.map { it.courseId }.toSet()
        val names = courseRepository.findCourseIdsByIdIn(courseIds).associate { it.id to it.courseId }
        return deliveries.map { DeliveryWithCourseName(it.id, it.startDate, names[it.courseId] ?: "<unknown>") }
    }

    @GetMapping
    fun index(model: Model): String {
        val request = PageRequest.of(0, PAGE_SIZE, START_DATE_DESC)
        val page = retrieveUpcomingDeliveriesWithCourse(request)

        model.addAttribute("page", page)

        return "index"
    }

    companion object {
        private val START_DATE_DESC = Sort.by(Sort.Direction.ASC, "startDate")
        const val PAGE_SIZE: Int = 10
    }

    data class DeliveryWithCourseName(
        val id: UUID?,
        val startDate: LocalDate,
        val courseId: String,
    )
}
