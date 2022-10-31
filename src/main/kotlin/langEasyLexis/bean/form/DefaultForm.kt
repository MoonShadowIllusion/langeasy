package langEasyLexis.bean.form

import langEasyLexis.api.Sign

class DefaultForm(userId:Int, token: String):Form(Sign._path) {
    init {
        this.user_id = userId.toString()
        this.token = token
    }
}