package com.zup.merchant.data.model.verifyOtp

import com.google.gson.annotations.SerializedName

data class EncryptedResponse(@SerializedName("status") var status : String,
                             @SerializedName("data") var data : String)