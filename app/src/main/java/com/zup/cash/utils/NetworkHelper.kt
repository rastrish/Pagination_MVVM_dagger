/*
 * copyright © ZERONE MICROSYSTEMS PRIVATE LIMITED. All rights reserved.
 */

package com.zup.merchant.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.util.logging.Logger
import javax.inject.Singleton

@Singleton
class NetworkHelper constructor(private val context: Context) {

    companion object {
        const val TAG = "NetworkHelper"
    }

    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

    fun castToNetworkError(throwable: Throwable): NetworkError {
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
            Log.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Log.e(TAG, e.toString())
        }
        return defaultNetworkError
    }
}