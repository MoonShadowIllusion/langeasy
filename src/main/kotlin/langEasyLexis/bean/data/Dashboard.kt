package langEasyLexis.bean.data

data class DashboardCalendar(
    val study: Int,
    val timestamp: Long,
    val sign_in: Int
)

data class DashboardSignIn(
    val continuous_days: Int,
    val total_days: Int
)