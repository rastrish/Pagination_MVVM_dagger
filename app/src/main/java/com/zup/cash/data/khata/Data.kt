package com.zup.merchant.data.model.khata

data class Data(
    val OriginalBankRRN: String,
    val __v: Int,
    val _id: String,
    val amount: String,
    val createdAt: String,
    val currency: String,
    val other: OtherX,
    val payeeId: String,
    val payerId: String,
    val rpStatus: RpStatus,
    val payerDetails : PayerDetails,
    val status: String,
    val transactionId: String,
    val transactionType: String,
    val updatedAt: String
)