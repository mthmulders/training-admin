package it.mulders.talks.trainingadmin.courses

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.support.expected
import assertk.assertions.support.show
import it.mulders.talks.trainingadmin.shared.unwrap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CourseRepositoryIT {
    @Autowired
    private lateinit var repository: CourseRepository

    @Autowired
    private lateinit var deliveryRepository: DeliveryRepository

    @Test
    fun `it should find a course by id`() {
        // Arrange
        val course = repository.save(Course(null, "TEST", "Test course", emptySet()))

        // Act
        val result = repository.findById(course.id!!).unwrap()

        // Assert
        assertThat(result).isNotNull().hasId()
    }

    @Test
    fun `it should retrieve deliveries for a course`() {
        // Arrange
        val course = repository.save(Course(null, "TESTWD", "Test course with delivery", emptySet()))
        val delivery = Delivery(null, LocalDate.now(), course.id!!)
        deliveryRepository.save(delivery)

        // Act
        val result = repository.findById(course.id).unwrap()

        // Assert
        assertThat(result).isNotNull().hasDeliveries()
    }

    fun Assert<Course>.hasId() = given { actual ->
        if (actual.id != null) return
        expected("to have a technical ID but was:${show(actual)}")
    }

    fun Assert<Course>.hasDeliveries() = given { actual ->
        if (!actual.deliveries.isEmpty()) return
        expected("to have at least one delivery but it  had none")
    }
}