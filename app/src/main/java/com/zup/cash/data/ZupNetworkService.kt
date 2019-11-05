package com.zup.cash.data

import com.zup.merchant.data.model.verifyOtp.EncryptedRequest
import com.zup.merchant.data.model.verifyOtp.EncryptedResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ZupNetworkService {

    @POST("login/mindorks")
    fun login():Single<User>

    @POST("getTransactions")
    fun getKhataDetails(
        @Header("token") token  : String,
        @Header("bearer") bearer  :String,
        @Body encryptedRequest: EncryptedRequest
    ):Single<EncryptedResponse>
}