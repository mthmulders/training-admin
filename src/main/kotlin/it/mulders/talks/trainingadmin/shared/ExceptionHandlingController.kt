package it.mulders.talks.trainingadmin.shared

import it.mulders.talks.trainingadmin.courses.CourseNotFoundException
import it.mulders.talks.trainingadmin.courses.DeliveryNotFoundException
import it.mulders.talks.trainingadmin.students.StudentNotFoundException
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

@ControllerAdvice
class ExceptionHandlingController {
    @ExceptionHandler(*[CourseNotFoundException::class, DeliveryNotFoundException::class, StudentNotFoundException::class])
    fun courseNotFound(exception: Exception): ModelAndView = ModelAndView("shared/error").apply {
        addObject("message", exception.message)
    }

    @ExceptionHandler(DuplicateKeyException::class)
    fun duplicateKey(exception: DuplicateKeyException): ModelAndView = ModelAndView("shared/error").apply {
        addObject("message", "That identifier is already in use")
    }
}