package langEasyLexis.bean.res

import langEasyLexis.api.Crypto

open class BaseRes {
    var result_code: Int = 0
    lateinit var data_kind: String
    lateinit var data_version: String
    lateinit var data_body: String
    var data_encrypted: Int = 0
    var error_body: error_body? = null
}

data class v3_security(
    val guest: Int,
    val token: String,
    val secret: String
)

data class error_body(
    val user_msg: String,
    val dev_info: String
)

class V3Res : BaseRes() {
    lateinit var v3_security: v3_security
    val v3_security_overwrite: Int = 0

    companion object {
        @JvmStatic
        fun decodeDataBody(str: String, key: String, iv: String): String {
            val b64 = Crypto.base64Decode(str.toByteArray())
            val end = Crypto.transerfer(b64, key, iv)
            return String(end, Charsets.UTF_8)
        }
    }
}
