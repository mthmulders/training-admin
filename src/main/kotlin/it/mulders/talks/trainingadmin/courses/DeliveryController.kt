package it.mulders.talks.trainingadmin.courses

import it.mulders.talks.trainingadmin.shared.unwrap
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/courses/edit/{courseId}")
class DeliveryController(
    private val courseRepository: CourseRepository,
    private val deliveryRepository: DeliveryRepository
) {
    @GetMapping("/delivery/new")
    fun create(@PathVariable courseId: String, model: Model): String = withCourse(courseId) {
        val delivery = Delivery(null, requireNotNull(it.id))
        model.addAttribute("course", it)
        model.addAttribute("delivery", delivery)
        "courses/delivery"
    }

    @PostMapping("/delivery/save")
    fun save(@PathVariable courseId: String, @Valid delivery: Delivery, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "courses/delivery"
        }

        return withDelivery(courseId, delivery) {
            val updated = it.id ?. let { id -> delivery.copy(id = id) } ?: delivery
            deliveryRepository.save(updated)
            "redirect:/courses/edit/$courseId"
        }
    }

    @GetMapping("/delivery/{deliveryId}/edit")
    fun edit(@PathVariable courseId: String, @PathVariable deliveryId: String, model: Model): String = withCourse(courseId) { course ->
        withDelivery(courseId, deliveryId) { delivery ->
            model.addAttribute("course", course)
            model.addAttribute("delivery", delivery)
            "courses/delivery"
        }
    }

    @GetMapping("/delivery/{deliveryId}/delete")
    fun delete(@PathVariable courseId: String, @PathVariable deliveryId: String, model: Model): String = withDelivery(courseId, deliveryId) {
        deliveryRepository.delete(it)
        "redirect:/courses/edit/$courseId"
    }

    private fun withCourse(courseId: String, action: (Course) -> String): String {
        return courseRepository.findByCourseId(courseId)
            ?.let { action(it) }
            ?: throw CourseNotFoundException(courseId)
    }

    private fun withDelivery(courseId: String, delivery: Delivery, action: (Delivery) -> String): String {
        return delivery.id ?. let {
            return deliveryRepository.findById(delivery.id).unwrap()
                ?.let { action(it) }
                ?: throw DeliveryNotFoundException(courseId, delivery.id.toString())
        } ?: action(delivery)
    }

    private fun withDelivery(courseId: String, deliveryId: String, action: (Delivery) -> String): String {
        return deliveryRepository.findById(UUID.fromString(deliveryId)).unwrap()
            ?.let { action(it) }
            ?: throw DeliveryNotFoundException(courseId, deliveryId)
    }
}