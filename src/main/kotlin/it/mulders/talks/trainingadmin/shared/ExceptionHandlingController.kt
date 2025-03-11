package it.mulders.talks.trainingadmin.shared

import it.mulders.talks.trainingadmin.courses.CourseNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

@ControllerAdvice
class ExceptionHandlingController {
    @ExceptionHandler(*[CourseNotFoundException::class])
    fun courseNotFound(exception: Exception): ModelAndView = ModelAndView("shared/error").apply {
        addObject("message", exception.message)
    }
}