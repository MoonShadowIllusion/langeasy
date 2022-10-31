package langEasyLexis.bean.res

data class RewardResult(
    val available: Int,
    val reward_data: RewardData
) {
    fun iSuccess(): Boolean {
        return available == 1
    }
}
