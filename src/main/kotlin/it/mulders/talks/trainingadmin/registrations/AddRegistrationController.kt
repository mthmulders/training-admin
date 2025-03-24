package it.mulders.talks.trainingadmin.registrations

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import it.mulders.talks.trainingadmin.courses.Course
import it.mulders.talks.trainingadmin.courses.CourseNotFoundException
import it.mulders.talks.trainingadmin.courses.CourseRepository
import it.mulders.talks.trainingadmin.courses.Delivery
import it.mulders.talks.trainingadmin.courses.DeliveryNotFoundException
import it.mulders.talks.trainingadmin.courses.DeliveryRepository
import it.mulders.talks.trainingadmin.shared.unwrap
import it.mulders.talks.trainingadmin.students.Student
import it.mulders.talks.trainingadmin.students.StudentNotFoundException
import it.mulders.talks.trainingadmin.students.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import java.time.LocalDate
import java.util.UUID

@Controller
@RequestMapping("/registrations")
@SessionAttributes(*["course", "delivery", "student"])
class AddRegistrationController(
    private val courseRepository: CourseRepository,
    private val deliveryRepository: DeliveryRepository,
    private val studentRepository: StudentRepository
) {
    @GetMapping("/add")
    fun addRegistrationForm(@RequestParam studentId: String?,
                            @RequestParam("courseId") courseId: String?,
                            @RequestParam("deliveryId") deliveryId: String?,
                            model: Model): String {
        if (studentId.isNullOrBlank()) {
            logger.info("Start of process")
            return "registrations/add-step-student"
        }

        if (courseId.isNullOrBlank()) {
            logger.info("Select course; student_id={}", studentId)
            return withStudent(studentId.toLong()) {
                model.addAttribute("student", it)
                "registrations/add-step-course"
            }
        }

        if (deliveryId == null) {
            return withStudent(studentId.toLong()) {
                model.addAttribute("student", it)
                withCourse(courseId) {
                    logger.info("Select delivery; student_id={}, course_id={}", studentId, courseId)
                    model.addAttribute("course", it)
                    model.addAttribute("result", deliveryRepository.findByCourseIdAndStartDateIsAfter(it.id!!, LocalDate.now()))
                    "registrations/add-step-delivery"
                }
            }
        }

        return withStudent(studentId.toLong()) {
            model.addAttribute("student", it)
            withCourse(courseId) {
                model.addAttribute("course", it)
                withDelivery(courseId, deliveryId) {
                    logger.info("Confirm; student_id={}, course_id={}, delivery_id={}", studentId, courseId, deliveryId)
                    model.addAttribute("delivery", it)
                    "registrations/add-step-confirm"
                }
            }
        }
    }

    @PostMapping("/add")
    fun processRegistration(model: Model,
                            sessionStatus: SessionStatus
    ): String {
        val course = model.getAttribute("course") as Course
        val student = model.getAttribute("student") as Student
        val delivery = model.getAttribute("delivery") as Delivery

        logger.info("Creating reservation; course_id={}, delivery_id={}, student_id={}", course.id, student.id, delivery.id)

        sessionStatus.setComplete()

        return "redirect:/"
    }


    @HxRequest
    @GetMapping("/search/student")
    fun searchStudent(@RequestParam("term") term: String = "someone", model: Model): String {
        logger.info("Student search; term={}", term)
        model.addAttribute("term", term)
        model.addAttribute("result", findStudentsByNameLike(term))

        return "registrations/student-search"
    }

    @HxRequest
    @GetMapping("/search/course")
    fun searchCourse(@RequestParam("term") term: String = "someone", model: Model): String {
        logger.info("Course search; term={}", term)
        model.addAttribute("term", term)
        model.addAttribute("result", findCourseByCodeLike(term))
        model.addAttribute("studentId", "")

        return "registrations/course-search"
    }

    private fun findStudentsByNameLike(term: String): Collection<Student> =
        studentRepository.findByFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase("%$term%", "%$term%")

    private fun findCourseByCodeLike(term: String): Collection<Course> =
        courseRepository.findByCourseIdLikeIgnoreCase("%$term%")

    private fun withStudent(studentId: Long, action: (Student) -> String): String {
        return studentRepository.findByStudentId(studentId)
            ?.let { action(it) }
            ?: throw StudentNotFoundException(studentId)
    }

    private fun withCourse(courseId: String, action: (Course) -> String): String {
        return courseRepository.findByCourseId(courseId)
            ?.let { action(it) }
            ?: throw CourseNotFoundException(courseId)
    }

    private fun withDelivery(courseId: String, deliveryId: String, action: (Delivery) -> String): String {
        return deliveryRepository.findById(UUID.fromString(deliveryId)).unwrap()
            ?.let { action(it) }
            ?: throw DeliveryNotFoundException(courseId, deliveryId)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AddRegistrationController::class.java)
    }
}
