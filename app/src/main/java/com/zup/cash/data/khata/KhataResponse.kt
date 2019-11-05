package com.zerone.zup.customer.data.model.khata

import com.zup.merchant.data.model.khata.Data

data class KhataResponse(
    val `data`: List<Data>?,
    val status: Int?
)