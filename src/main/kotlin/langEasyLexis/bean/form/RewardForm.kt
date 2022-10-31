package langEasyLexis.bean.form

import langEasyLexis.api.ReviewReward
import langEasyLexis.api.SpellReward
import langEasyLexis.api.StudyReward
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

abstract class RewardForm(type: String, userId: Int, override var token: String) : Form(type) {
    var date_list = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale.CHINA))

    init {
        this.user_id = userId.toString()
    }
}

class StudyRewardForm(userId: Int, token: String) : RewardForm(StudyReward._path, userId, token) {

}

class SpellRewardForm(userId: Int, token: String) : RewardForm(SpellReward._path, userId, token) {

}

class ReviewRewardForm(userId: Int, token: String) : RewardForm(ReviewReward._path, userId, token) {

}