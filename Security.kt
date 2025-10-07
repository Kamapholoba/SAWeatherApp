package com.saweatherplus.util

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64

object Security {
    private const val ITERATIONS = 10000
    private const val KEY_LENGTH = 256

    fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    fun hashPassword(password: CharArray, salt: ByteArray): String {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val key = skf.generateSecret(spec).encoded
        return Base64.encodeToString(key, Base64.NO_WRAP)
    }
}
