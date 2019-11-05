package com.zup.merchant.data.model.khata

data class OtherX(
    val _id: String,
    val amount_due: Int,
    val amount_paid: Int,
    val attempts: Int,
    val billNumber: String,
    val createdAt: String,
    val created_at: Int,
    val currency: String,
    val entity: String,
    val notes: List<Any>,
    val offer_id: Any,
    val orderId: String,
    val status: String,
    val updatedAt: String
)