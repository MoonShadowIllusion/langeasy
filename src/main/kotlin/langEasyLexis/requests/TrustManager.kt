package langEasyLexis.requests

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

object TrustManager {
    @JvmStatic
    fun getTrustManager(): Array<X509TrustManager> {
        return arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }

}
