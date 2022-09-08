package langEasyLexis.bean.res

import langEasyLexis.bean.data.DashboardCalendar
import langEasyLexis.bean.data.DashboardSignIn
import langEasyLexis.bean.data.Privileges
import langEasyLexis.bean.data.UserInfo

data class LoginResult(
    val user_info: UserInfo,
    val bindings: Bindings,
    val privileges: Privileges,
    val user_status: UserStatus,
    val actions: Actions,
    val base_info: BaseInfo,
    val bb_preference: BbPreference,
    val dashboard_sign_in: DashboardSignIn,
    val dashboard_calendar: List<DashboardCalendar>
)

data class UserInfo(
    val id: Int,
    val nickname: String,
    val password: String,
    val Email: String,
    val avatar_image: String,
    val is_register: Int,
    val new_user: Int,
    val is_new_user: Int,
    val phone: String
)

data class Bindings(
    val primary_binding: String,
    val sns_bindings: SnsBindings
)

data class Privileges(
    val bbvip: Bbvip,
    val collins: Collins,
    val wordroot: Wordroot,
    val wordderive: Wordderive,
    val xyjc: List<Any>,
    val bbnote: Bbnote
)

data class UserStatus(
    val total_coin: Int,
    val task_status: TaskStatus,
    val sign_in_continuous_days: Int,
    val login_prompt: String,
    val login_prompt_platform: String,
    val logged_off: Int
)

data class Actions(
    val launch_card_action: LaunchCardAction,
    val float_button_action: FloatButtonAction,
    val web_page: WebPage,
    val bind_phone_action: Int
)

data class BaseInfo(
    val coin_reward: CoinReward,
    val config: Config
)

data class BbPreference(
    val assist_memory_tags: String,
    val auto_pronounce: String,
    val auto_pronounce_lexis_sentence: String,
    val auto_pronounce_sent_sentence: String,
    val choice_quiz_detail: String,
    val group_word_quantity: String,
    val listen_word_choice_quiz: String,
    val sentence_translation: String,
    val spelling_practice: String,
    val spelling_quiz: String,
    val split_word_when_spell: String,
    val study_mode: String,
    val v5_learn_mode_card_display: String
)

data class SnsBindings(
    val sina: Int,
    val wechat: Int,
    val qzone: Int,
    val huawei: Int,
    val oppo: Int,
    val xiaomi: Int,
    val apple: Int
)

data class Bbvip(
    val granted: Int,
    val expire_date: Int
)

data class Collins(
    val granted: Int,
    val expire_date: Int,
    val collins_user_type: Int
)

data class Wordroot(
    val granted: Int,
    val expire_date: Int,
    val user_type: Int
)

data class Wordderive(
    val granted: Int,
    val expire_date: Int,
    val user_type: Int
)

data class Bbnote(
    val granted: Int,
    val expire_date: Int,
    val user_type: Int
)

data class TaskStatus(
    val finish_study: Int,
    val share_to_sns: Int,
    val finish_spell: Int,
    val finish_review: Int
)

data class LaunchCardAction(
    val id: Int,
    val type: Int,
    val title: String,
    val message: String,
    val image: String,
    val hideclose: Int,
    val buttons: List<Button>,
    val enable: Int
)

data class FloatButtonAction(
    val id: Int,
    val button_id: Int,
    val image: String,
    val hideclose: Int,
    val reopen: Int,
    val url: String,
    val right: Int,
    val top: Int,
    val width: Int,
    val height: Int,
    val enable: Int
)

data class WebPage(
    val enable: Int,
    val url: String
)

data class Button(
    val name: String,
    val type: Int,
    val `data`: String
)

data class CoinReward(
    val finish_study: Int,
    val finish_spell: Int,
    val finish_review: Int,
    val share_to_sns: Int,
    val signin: Int
)

data class Config(
    val upload_user_action: Int
)