package com.example.paymenthistory.data

/**
 * Data class representing a payment entity.
 *
 * @property id The unique identifier for the payment.
 * @property title The title or description associated with the payment (nullable).
 * @property amount The amount of the payment (nullable).
 * @property created The timestamp indicating when the payment was created (nullable).
 */
data class Payment(
    val id: Long,
    val title: String?,
    val amount: String?,
    val created: String?
)
