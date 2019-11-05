package com.zup.cash.ui.khata

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.zerone.zup.customer.data.model.khata.KhataResponse
import com.zup.cash.R
import com.zup.cash.ZupRepository
import com.zup.cash.data.Networking
import com.zup.cash.data.ZupNetworkService
import com.zup.cash.utils.common.Resource
import com.zup.cash.utils.rx.RxSchedulerProvider
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.data.model.khata.Data
import com.zup.merchant.data.model.khata.KhataListResponse
import com.zup.merchant.data.model.verifyOtp.EncryptedRequest
import com.zup.merchant.data.model.verifyOtp.EncryptedResponse
import com.zup.merchant.utils.encryption.AESCiper
import com.zup.merchant.utils.encryption.RSACiper
import com.zup.merchant.utils.network.NetworkError
import com.zup.merchant.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException
import javax.net.ssl.HttpsURLConnection

class ItemDataSource(
    val compositeDisposable: CompositeDisposable,
    private val rsaCiper: RSACiper,
    private val aesCiper: AESCiper,
    val schedulerProvider: SchedulerProvider,
    private val zupRepository: ZupRepository
) : PageKeyedDataSource<Int, Data>() {


    companion object {

        val PAGE_SIZE = 10
        private val FIRST_PAGE = 0
         const val barer = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVkOWYyZTA2ZDRhZGMzNGJjMTRkZDBkZSIsImlhdCI6MTU3MjkyOTQ3NiwiZXhwIjoxNTc0MjI1NDc2fQ.h-PjcYJOrp91Nrund21jXFBGjL9_KVkwHTuxQRJ0eEE"
    }

    var encryptedRequest = EncryptedRequest(null, null)
    var khataListResponse = KhataListResponse(null)
    var gson = Gson()



    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Data>
    ) {

        val jsonObject = JSONObject()
        jsonObject.put("offset", FIRST_PAGE.toString())
        Log.e("json", gson.toJson(jsonObject))

        val data = aesCiper.encrypt(jsonObject.toString())
        val enData = data!!.first.replace("\\s".toRegex(), "")
        val aesKey = data.second
        var token = rsaCiper.encrypt(aesKey)
        token = token.replace("\\s".toRegex(), "")

        Log.e("token", token)
        Log.e("data", enData)
        encryptedRequest = EncryptedRequest(enData, data.third)

        compositeDisposable.addAll(
            zupRepository.getkhataDetails(
                token,
                barer,
                encryptedRequest
            ).subscribeOn(schedulerProvider.io())?.subscribe(
                {
//                    Log.e("response", gson.toJson(it))
                    val responseData = aesCiper.decrypt(it.data.trim(), data.third, aesKey)
                    Log.e("responseData", responseData)
                    khataListResponse = gson.fromJson(responseData, KhataListResponse::class.java)

                    val listOfData: List<Data> = khataListResponse.list!!
                    callback.onResult(listOfData, null, FIRST_PAGE + 1)
                },
                {
                    castToNetworkError(it)

                }

            )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Data>
    ) {

        val jsonObject = JSONObject()
        jsonObject.put("offset", params.key.toString())
        Log.e("json", gson.toJson(jsonObject))

        val data = aesCiper.encrypt(jsonObject.toString())
        val enData = data!!.first.replace("\\s".toRegex(), "")
        val aesKey = data.second
        var token = rsaCiper.encrypt(aesKey)
        token = token.replace("\\s".toRegex(), "")

        Log.e("token", token)
        Log.e("data", enData)
        encryptedRequest = EncryptedRequest(enData, data.third)
        compositeDisposable.addAll(
            zupRepository.getkhataDetails(
                token,
                barer,
                encryptedRequest
            ).subscribeOn(schedulerProvider.io())?.subscribe(
                {
                    val responseData = aesCiper.decrypt(it.data, data.third, aesKey)
                    Log.e("responseData", responseData)
                    khataListResponse = gson.fromJson(responseData, KhataListResponse::class.java)
                    val adjacentKey = (if (params.key > 1) params.key - 1 else null)!!.toInt()
                    val listOfData: List<Data> = khataListResponse.list!!
                    callback.onResult(listOfData, adjacentKey)
                },
                {
                    castToNetworkError(it)
                }
            )
        )
    }


    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Data>
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("offset", params.key.toString())
        Log.e("json", gson.toJson(jsonObject))
        val data = aesCiper.encrypt(jsonObject.toString())
        val enData = data!!.first.replace("\\s".toRegex(), "")
        val aesKey = data.second
        var token = rsaCiper.encrypt(aesKey)
        token = token.replace("\\s".toRegex(), "")

        Log.e("token", token)
        Log.e("data", enData)
        encryptedRequest = EncryptedRequest(enData, data.third)
        compositeDisposable.addAll(
            zupRepository.getkhataDetails(
                token,
                barer,
                encryptedRequest
            ).subscribeOn(schedulerProvider.io())?.subscribe(
                {
                    if (it.data != null && true) {
                        val responseData = aesCiper.decrypt(it.data, data.third, aesKey)
                        Log.e("responseData", responseData)
                        khataListResponse =
                            gson.fromJson(responseData, KhataListResponse::class.java)

                        val listOfData: List<Data> = khataListResponse.list!!
                        callback.onResult(listOfData, params.key + 1)
                    }
                },
                {
                    castToNetworkError(it)
                }
            ))
    }


    private fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {
            if (throwable is ConnectException) return NetworkError(0, "0")
            if (throwable !is HttpException) return defaultNetworkError
            val error = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(throwable.response().errorBody()?.string(), NetworkError::class.java)
            return NetworkError(throwable.code(), error.statusCode, error.message)
        } catch (e: IOException) {
            Log.e(NetworkHelper.TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            Log.e(NetworkHelper.TAG, e.toString())
        } catch (e: NullPointerException) {
            Log.e(NetworkHelper.TAG, e.toString())
        }
        return defaultNetworkError
    }


}