package langEasyLexis.bean.form

import langEasyLexis.SocializeConstants
import langEasyLexis.api.Login
import okhttp3.FormBody


class LoginForm(val account: String,val pw: String) : Form(Login._path) {
    override var token = SocializeConstants.DEFAULT_TOKEN


    fun getForm(): FormBody {
        return this
            .getFormBodyBuilder()
            .appSign(SocializeConstants.V3SecretKey, SocializeConstants.CoolApiIV)
            .build()
    }

}