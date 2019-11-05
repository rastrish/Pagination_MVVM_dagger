package com.zup.merchant.utils.encryption

import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import android.util.Log


class AESCiper {

    private var key: String
    private var initVector: String


    init {
        key = getRandomString(16)
        initVector = getRandomString(16)
    }


    fun encrypt(value: String): Triple<String, String, String>? {
        try {
            Log.e("iv", initVector)
            Log.e("key", key)
            val iv = IvParameterSpec(initVector.toByteArray(StandardCharsets.UTF_8))
            val secretKeySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
            val ciper = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            ciper.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
            val encrypted = ciper.doFinal(value.toByteArray())
            return Triple(Base64.encodeToString(encrypted, Base64.DEFAULT), key, initVector)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun decrypt(encrypted: String, iv: String, key: String): String? {
        try {
            val ivv = IvParameterSpec(iv.toByteArray(StandardCharsets.UTF_8))
            val secretKeySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivv)
            val orignal = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
            return String(orignal)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun getRandomString(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}



