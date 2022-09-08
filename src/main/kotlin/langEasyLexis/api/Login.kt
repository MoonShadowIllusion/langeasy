package langEasyLexis.api

import java.nio.charset.StandardCharsets.UTF_8

object Login: BaseApi() {

    override var _path = "2/login/by-account"


    @JvmStatic
    fun getPasswordEncode(str: String): String {
        val bytes = str.toByteArray(UTF_8)
        val length = bytes.size
        val b = (length * 73).toByte()
        for (i in 0 until length) {
            bytes[i] = (bytes[i].toInt() xor b.toInt()).toByte()
            if (bytes[i].toInt() == 0) {
                bytes[i] = 1
            }
        }
        return Crypto.md5String(bytes)
    }
}