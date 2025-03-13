package it.mulders.talks.trainingadmin.courses

class DeliveryNotFoundException(courseId: String?, deliveryId: String?) : Exception(createMessage(courseId, deliveryId)) {
    companion object {
        private fun createMessage(courseId: String?, deliveryId: String?): String = courseId
            ?.let { deliveryId?.let { "Delivery '$deliveryId' for course '$courseId' was not found." } }
            ?: "Delivery was not found."
    }
}
