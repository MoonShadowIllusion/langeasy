package langEasyLexis.bean.data

data class taskStatus(
    val finish_study: Int,
    val share_to_sns: Int,
    val finish_spell: Int,
    val finish_review: Int

)

data class userStatus(
    val total_coin: Int,
    val task_status:taskStatus,
    val sign_in_continuous_days:Int,
    val login_prompt:String,
    val login_prompt_platform:String,
    val logged_off:Int
)
