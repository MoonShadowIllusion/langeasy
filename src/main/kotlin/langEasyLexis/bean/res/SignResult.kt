package langEasyLexis.bean.res

import langEasyLexis.bean.data.DashboardCalendar
import langEasyLexis.bean.data.DashboardSignIn

data class SignResult(
    val reward: Reward,
    val dashboard_sign_in: DashboardSignIn,
    val dashboard_calendar: List<DashboardCalendar>
)

data class Reward(
    val available: Int,
    val reward_data: RewardData
)

data class RewardData(
    val server_time: String,
    val total: UInt,
    val continuous_days: UInt,
    val v3_si_achievement: Int,
    val user_message: String,
    val v3_si_t1: String,
    val coin: UInt,
    val v3_si_t2: String
)