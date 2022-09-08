import langEasyLexis.SocializeConstants
import langEasyLexis.TestConfig
import langEasyLexis.api.BaseApi
import okhttp3.*
import java.net.URL
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import langEasyLexis.requests.TrustManager as ReqTrustManager


object Requests {
    private const val PROTOCOL = "https"
    private const val HOST = "api.beingfine.cn"
    const val CONTENT_TYPE = "Content-Type"
    const val APPLICATION_URLENCODED = "application/x-www-form-urlencoded"
    private const val USER_AGENT = "okhttp/${SocializeConstants.OKHTTP_VERSION} bbdc/${SocializeConstants.APP_VERSION}"

    private var sslClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
            .readTimeout(120L, TimeUnit.SECONDS)
            .connectTimeout(5L, TimeUnit.SECONDS)
            .proxy(TestConfig.proxy)

        /* bbdc
        val cer = this::class.java.getResourceAsStream("bbdc.cer")
        val x509 = CertificateFactory.getInstance("X.509").generateCertificate(cer) as X509Certificate

        val name = x509.subjectX500Principal.name

        val sslContext = SSLContext.getInstance("SSL")
        val trustManagerArray = arrayOf(TrustManager(name,x509))
        sslContext.init(null, trustManagerArray, SecureRandom())
        builder.sslSocketFactory(sslContext.socketFactory,trustManagerArray[0])*/

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, ReqTrustManager.getTrustManager(), SecureRandom())
        sslContext.socketFactory
        builder.sslSocketFactory(sslContext.socketFactory, ReqTrustManager.getTrustManager()[0])
        if (TestConfig.use_proxy)
            builder.proxy(TestConfig.proxy)
        sslClient = builder.build()
    }


    private fun getBuilder(path: String): Request.Builder {

        return Request.Builder()
            .url(URL(PROTOCOL, HOST, path))
            .addHeader("User-Agent", USER_AGENT)
            .addHeader("Referer", "https://app.bbdc.cn")
    }

    fun GET(api: BaseApi, headers: Headers?, params: String?): Response {
        val path = StringBuilder(api.getPath())
        path.append(params)

        val builder = getBuilder(path.toString())

        headers?.forEach {
            builder.addHeader(it.first, it.second)
        }
        val req = builder.get().build()
        return sslClient.newCall(req).execute()
    }

    fun POST(api: BaseApi, headers: Headers?, body: RequestBody): Response {
        val builder = getBuilder(api.getPath())
        headers?.forEach {
            builder.addHeader(it.first, it.second)
        }
        val req = builder.post(body).build()
        return sslClient.newCall(req).execute()
    }

}