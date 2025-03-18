package it.mulders.talks.trainingadmin.students

import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/students")
class StudentController(
    private val studentRepository: StudentRepository
) {
    @GetMapping("/list")
    fun list(model: Model, @RequestParam("page") pageNumber: Int?): String {
        val page = studentRepository.findAll(PageRequest.of(pageNumber ?: 0, StudentController.PAGE_SIZE, LASTNAME_DESC))
        model.addAttribute("page", page)

        return "students/list"
    }

    @GetMapping("/new")
    fun create(model: Model): String {
        model.addAttribute("student", Student())

        return "students/edit"
    }

    @PostMapping("/save")
    fun save(@Valid student: Student, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "students/edit"
        }

        return withStudent(student) {
            val updated = it.id ?. let { id -> student.copy(id = id) } ?: student.copy(studentId = null)
            studentRepository.save(updated)
            "redirect:/students/list"
        }
    }

    @GetMapping("/edit/{studentId}")
    fun edit(@PathVariable studentId: String, model: Model): String = withStudent(studentId.toLong()) {
        model.addAttribute("student", it)
        "students/edit"
    }

    @GetMapping("/delete/{studentId}")
    fun delete(@PathVariable studentId: String, model: Model): String = withStudent(studentId.toLong()) {
        studentRepository.delete(it)
        "redirect:/students/list"
    }

    private fun withStudent(student: Student, action: (Student) -> String): String {
        return student.id
            ?.let { withStudent(student.studentId!!, action) }
            ?: action(student)
    }

    private fun withStudent(studentId: Long, action: (Student) -> String): String {
        return studentRepository.findByStudentId(studentId)
                    ?.let { action(it) }
                    ?: throw StudentNotFoundException(studentId)
    }

    companion object {
        const val PAGE_SIZE: Int = 10
        private val LASTNAME_DESC = Sort.by(Sort.Direction.ASC, "lastName")
    }
}