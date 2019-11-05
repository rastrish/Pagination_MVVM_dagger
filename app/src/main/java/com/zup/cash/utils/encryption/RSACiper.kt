package com.zup.merchant.utils.encryption
import android.util.Base64

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



class RSACiper @Throws(
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidKeyException::class,
    IllegalBlockSizeException::class,
    BadPaddingException::class) constructor() {

    lateinit var  keyPairGenerator : KeyPairGenerator
    lateinit var keyPair: KeyPair
    lateinit var publicKey: PublicKey
    lateinit var privateKey: PrivateKey
    lateinit var encryptedBytes: ByteArray
    lateinit var decryptedBytes:ByteArray
    lateinit var cipher: Cipher
    lateinit var cipher1:Cipher
    lateinit var decrypted:String
    private val CRYPTO_METHOD = "RSA"
    private val CRYPTO_BITS = 2048


    init {
        generateKeyPair()
        getPublicKey("pkcs1-pem")
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )

    private fun generateKeyPair() {

        keyPairGenerator = KeyPairGenerator.getInstance(CRYPTO_METHOD)
        keyPairGenerator.initialize(CRYPTO_BITS)
        keyPair = keyPairGenerator.genKeyPair()
        publicKey = keyPair.public
        privateKey = keyPair.private
    }


    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encrypt(vararg args: Any): String {


        val plain = args[0] as String
        val rsaPublicKey: PublicKey = stringToPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA10XCk0brYmVW8D9W6X1RLLwkwkHDcZ9gUwJORw06/VcRUvoXQuehwUeh8FmV6dxYHviX8gqqw+O1QblJGWhGN20G+strDmPozm9e/+ThsxZ/F2szmB2bV/PRTdz7zxMFABRvU38vrwqft9AMgWO6JlkfSC4ONVN8wkWaKRZqmkvriMsqwZ3C0rkMt5k/3PKN2kf1NOwsssNdHIvZQ2sA/K4Z+m9ZuUI9GXGi1FeWTGLqXkiS76JbW+mxNdogqeT/ND3Rgvp3Iu4huaGDlCyyoFHozzU0Wa6QR2JZ7ePbOL0L9EfV+/5WIVndxgyS3ut09Aj0+tv8vFNCU70uWLqGVwIDAQAB")!!
        if (args.size == 1) {
            this.publicKey
        } else {
            args[1] as PublicKey
        }

        cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey)
        encryptedBytes = cipher.doFinal(plain.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decrypt(result: String): String {

        cipher1 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
        cipher1.init(Cipher.DECRYPT_MODE, privateKey)
        decryptedBytes = cipher1.doFinal(Base64.decode(result, Base64.DEFAULT))
        decrypted = String(decryptedBytes)

        return decrypted
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )

    fun getPublicKey(option: String): String? {

        when (option) {

            "pkcs1-pem" -> {
                var pkcs1pem = "-----BEGIN RSA PUBLIC KEY-----\n"
                pkcs1pem += Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)
                pkcs1pem += "-----END RSA PUBLIC KEY-----"

                return pkcs1pem
            }

            "pkcs8-pem" -> {
                var pkcs8pem = "-----BEGIN PUBLIC KEY-----\n"
                pkcs8pem += Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)
                pkcs8pem += "-----END PUBLIC KEY-----"

                return pkcs8pem
            }

            "base64" -> return Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)

            else -> return null
        }

    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun stringToPublicKey(publicKeyString: String): PublicKey? {
        var publicKeyString = publicKeyString

        try {
            if (publicKeyString.contains("-----BEGIN PUBLIC KEY-----") || publicKeyString.contains("-----END PUBLIC KEY-----"))
                publicKeyString = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
            val keyBytes = Base64.decode(publicKeyString, Base64.DEFAULT)
            val spec = X509EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance("RSA")

            return keyFactory.generatePublic(spec)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()

            return null
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
            return null
        }

    }


}