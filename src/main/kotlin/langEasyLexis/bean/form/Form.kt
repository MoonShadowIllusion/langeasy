package langEasyLexis.bean.form

import langEasyLexis.SocializeConstants
import langEasyLexis.api.Crypto
import okhttp3.FormBody
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

typealias FBBuilderANDMap = Pair<FormBody.Builder, HashMap<String, String>>

open class Form(val type: String) {
    var device: String
    open lateinit var token: String
    var version: String
    var app_id: String
    var umid: String
    var timestamp: String
    var udid: String
    var channel: String
    var user_id: String? = null
    var sign: String? = null

    init {
        channel = SocializeConstants.CHANNEL
        device = SocializeConstants.DEVICE
        version = SocializeConstants.APP_VERSION
        app_id = SocializeConstants.APP_ID
        umid = SocializeConstants.UMID
        timestamp = System.currentTimeMillis().toString()
        udid = SocializeConstants.UDID
    }

    fun getFormBodyBuilder(): FBBuilderANDMap {
        val builder = FormBody.Builder()
        val hashMap = hashMapOf<String, String>()
        this::class.memberProperties.forEach {
            val value = it.call(this)
            if (value != null) {
                it.isAccessible = true
                hashMap[it.name] = value as String
                builder.add(it.name, value)
            }
        }
        return Pair(builder, hashMap)
    }

    open fun getFrom(key: String): FormBody {
        return this.getFormBodyBuilder()
            .appSign(key, SocializeConstants.CoolApiIV)
            .build()
    }

    fun getParams(key: String): String {
        val body = getFrom(key)
        val sb = StringBuilder("?")
        body.run {
            for (i in 0 until body.size) {
                sb.append(this.name(i), "=", this.encodedValue(i), "&")
            }
        }
        return sb.deleteCharAt(sb.length - 1).toString()
    }

}

fun Map<String, String>.getContent(): String {
    if (this.isEmpty()) {
        return ""
    }
    val sb = StringBuilder()
    val arrayList = ArrayList(this.keys)
    arrayList.sort()
    var i = 0
    while (i < arrayList.size) {
        val str = arrayList[i] as String
        val str2 = this[str]
        sb.append(if (i == 0) "" else "&")
        sb.append(str)
        sb.append("=")
        sb.append(str2)
        i++
    }
    return sb.toString()
}

fun FBBuilderANDMap.appSign(key: String, iv: String): FormBody.Builder {
    val con = this.second.getContent()
    val aesEnd = Crypto.trans(con.toByteArray(), key, iv)
    val b64 = Crypto.base64Encode(aesEnd).replace(" ", "")
    this.first.add("sign", Crypto.md5StringWithUtf8(b64))

    return this.first
}