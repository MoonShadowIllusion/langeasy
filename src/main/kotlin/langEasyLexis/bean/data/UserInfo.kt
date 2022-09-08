package langEasyLexis.bean.data

data class UserInfo(
    val id: Int,
    val nickname: String,
    val password: String,
    val Email: String,
    val avatar_image: String,
    val is_register: Int,
    val new_user: Int,
    val is_new_user: Int,
    val phone: String,
)
