package langEasyLexis.api

import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object Crypto {
    var hex = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    fun md5StringWithUtf8(str: String): String {
        return md5String(str.toByteArray(UTF_8))
    }

    fun md5String(bArr: ByteArray?): String {
        return try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(bArr)
            val dig = md5.digest()
            byte2str(dig)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    private fun byte2str(bytes: ByteArray): String {
        val len = bytes.size
        val result = StringBuffer()
        for (i in 0 until len) {
            val byte0 = bytes[i]
            result.append(hex[byte0.toInt() ushr 4 and 0xf])
            result.append(hex[byte0.toInt() and 0xf])
        }
        return result.toString().lowercase(Locale.getDefault())
    }

    fun base64Encode(bytes: ByteArray):String{
        return String(Base64.getEncoder().encode(bytes))
    }

    fun base64Decode(bytes: ByteArray):ByteArray{
        return Base64.getDecoder().decode(bytes)
    }



    fun trans(bArr: ByteArray?, str: String, str2: String): ByteArray {
        val secretKeySpec = SecretKeySpec(str.toByteArray(UTF_8), "AES")
        val ivParameterSpec = IvParameterSpec(str2.toByteArray(UTF_8))
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(1, secretKeySpec, ivParameterSpec)
        return cipher.doFinal(bArr)
    }


    fun transerfer(bArr: ByteArray?, str: String, str2: String): ByteArray {
        val secretKeySpec = SecretKeySpec(str.toByteArray(UTF_8), "AES")
        val ivParameterSpec = IvParameterSpec(str2.toByteArray(UTF_8))
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(2, secretKeySpec, ivParameterSpec)
        return cipher.doFinal(bArr)
    }


}