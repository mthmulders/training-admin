package it.mulders.talks.trainingadmin.courses

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/courses")
class CoursesController(private val courseRepository: CourseRepository) {
    @GetMapping("/list")
    fun list(model: Model, @RequestParam("page") pageNumber: Int?, request: HttpServletRequest): String {
        val log = LoggerFactory.getLogger(CoursesController::class.java)
        request.headerNames.toList().forEach { name ->
            request.getHeaders(name).toList().forEach { value ->
                log.info("header {}={}", name, value)
            }
        }

        val page = courseRepository.findAll(PageRequest.of(pageNumber ?: 0, PAGE_SIZE))
        model.addAttribute("page", page)

        return "courses/list"
    }

    @GetMapping("/new")
    fun create(model: Model): String {
        model.addAttribute("course", Course())
        return "courses/edit"
    }

    @GetMapping("/edit/{courseId}")
    fun edit(@PathVariable courseId: String, model: Model): String = withCourse(courseId) {
        model.addAttribute("course", it)
        "courses/edit"
    }

    @PostMapping("/save")
    fun save(@Valid course: Course, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "courses/edit"
        }

        return withCourse(course) {
            val updated = it.id ?. let { id -> course.copy(id = id) } ?: course
            courseRepository.save(updated)
            "redirect:/courses/list"
        }
    }

    @GetMapping("/delete/{courseId}")
    fun delete(@PathVariable courseId: String, model: Model): String = withCourse(courseId) {
        courseRepository.delete(it)
        "redirect:/courses/list"
    }

    private fun withCourse(course: Course, action: (Course) -> String): String {
        return course.id
            ?.let { withCourse(course.courseId, action) }
            ?: action(course)
    }

    private fun withCourse(courseId: String, action: (Course) -> String): String {
        return courseRepository.findByCourseId(courseId)
                    ?.let { action(it) }
                    ?: throw CourseNotFoundException(courseId)
    }

    companion object {
        const val PAGE_SIZE: Int = 10;
    }
}