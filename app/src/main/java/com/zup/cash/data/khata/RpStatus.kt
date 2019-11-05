package com.zup.merchant.data.model.khata

data class RpStatus(
    val _id: String,
    val calculated_signature: String,
    val createdAt: String,
    val razorpay_order_id: String,
    val razorpay_payment_id: String,
    val razorpay_signature: String,
    val signatureMatched: Boolean,
    val transactionId: String,
    val updatedAt: String
)