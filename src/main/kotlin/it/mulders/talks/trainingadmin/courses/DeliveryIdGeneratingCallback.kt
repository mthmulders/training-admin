package it.mulders.talks.trainingadmin.courses

import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DeliveryIdGeneratingCallback : BeforeConvertCallback<Delivery> {
    override fun onBeforeConvert(delivery: Delivery): Delivery {
        return delivery.id
            ?. let { delivery }
            ?: delivery.copy(id = UUID.randomUUID())
    }
}
