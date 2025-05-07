package it.mulders.talks.trainingadmin.courses

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import java.time.LocalDate
import java.util.UUID
import kotlin.test.Test

class DeliveryIdGeneratingCallbackTest {
    private val callback = DeliveryIdGeneratingCallback()

    @Test
    fun `it should generate a valid DeliveryId`() {
        val delivery = Delivery(null, LocalDate.now(), UUID.randomUUID())

        val result = callback.onBeforeConvert(delivery)

        assertThat(result.id).isNotNull()
    }

    @Test
    fun `it should not override an existing DeliveryId`() {
        val delivery = Delivery(UUID.randomUUID(), LocalDate.now(), UUID.randomUUID())

        val result = callback.onBeforeConvert(delivery)

        assertThat(result.id).isEqualTo(delivery.id)
    }
}