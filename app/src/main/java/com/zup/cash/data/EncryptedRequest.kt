package com.zup.merchant.data.model.verifyOtp

import com.google.gson.annotations.SerializedName

data class EncryptedRequest(@SerializedName("data") var data : String?,@SerializedName("iv") var iv : String?)