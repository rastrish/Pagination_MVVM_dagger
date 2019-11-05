package com.zup.cash

import com.zup.cash.data.User
import com.zup.cash.data.ZupNetworkService
import com.zup.merchant.data.model.verifyOtp.EncryptedRequest
import com.zup.merchant.data.model.verifyOtp.EncryptedResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZupRepository @Inject constructor(
    private val networkService: ZupNetworkService) {

    fun login(phoneNumber: String?): Single<User> =
        networkService.login()

    fun getkhataDetails(token : String , bearer : String ,  encryptedRequest: EncryptedRequest) : Single<EncryptedResponse>{
        return networkService.getKhataDetails(token,bearer,encryptedRequest)
    }

}