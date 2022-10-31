package langEasyLexis

import Requests
import com.google.gson.Gson
import dataBase.DataBase
import dataBase.Socialize
import langEasyLexis.api.*
import langEasyLexis.bean.form.*
import langEasyLexis.bean.res.LoginResult
import langEasyLexis.bean.res.RewardResult
import langEasyLexis.bean.res.SignResult
import langEasyLexis.bean.res.V3Res
import langEasyLexis.exception.LoginFailure
import okhttp3.Headers
import okhttp3.Response
import push.PushPlus

class User(private val account: String, private val password: String) {
    lateinit var token: String
    lateinit var secret: String
    lateinit var loginResult: LoginResult

    companion object {
        @JvmStatic
        fun getInstance(account: String, password: String): User {
            return User(account, Login.getPasswordEncode(password))
        }
    }

    fun refresh(v3Res: V3Res) {
        secret = v3Res.v3_security.secret
        token = v3Res.v3_security.token
    }


    fun login() {
        val res = Requests.POST(
            Login,
            Headers.headersOf(Requests.CONTENT_TYPE, Requests.APPLICATION_URLENCODED),
            LoginForm(account, password).getForm()
        )
        val json = Gson().fromJson(res.body!!.charStream(), V3Res::class.java)
        if (json.iSuccess()) {
            refresh(json)
            val deRes = V3Res.decodeDataBody(json.data_body, secret, SocializeConstants.CoolApiIV)
            loginResult = Gson().fromJson(deRes, LoginResult::class.java)

            println("登录成功 ${loginResult.user_info.nickname} ${loginResult.user_status.total_coin}币")
        } else {
            throw LoginFailure(json.error_body!!.user_msg)
        }
    }

    fun sign(): String {
        val res = Requests.GET(
            Sign,
            null,
            DefaultForm(loginResult.user_info.id, token).getParams(secret)
        )
        val signRes = this.resHandle(res, SignResult::class.java)
        Socialize.dataBase.log(
            account,
            signRes.reward.reward_data.coin,
            signRes.reward.reward_data.total,
            DataBase.log_type.sign, "连续签到 ${signRes.reward.reward_data.continuous_days} 天"
        )
        println(signRes.reward.reward_data.user_message)
        val l1 = signRes.reward.reward_data.v3_si_t1.replace("\n", "").replace("<b>", "").replace("</b>", "")
        val l2 = signRes.reward.reward_data.v3_si_t2.replace("\n", "").replace("<b>", "").replace("</b>", "")

        println(l1)
        if (l1 != l2)
            println(l2)

        return l1

        /*
        <b>+10</b> 酷币
        连续签到 <b>1</b> 天
         */
    }

    fun logout() {
        val res = Requests.GET(
            Logout,
            Headers.headersOf(Requests.CONTENT_TYPE, Requests.APPLICATION_URLENCODED),
            DefaultForm(loginResult.user_info.id, token).getParams(secret)
        )
        val json = Gson().fromJson(res.body!!.charStream(), V3Res::class.java)
        if (json.iSuccess()) {
            val deRes = V3Res.decodeDataBody(json.data_body, secret, SocializeConstants.CoolApiIV)
            println("退出成功")
            //不需要
        }
    }

    fun rewardByStudy(): String {
        val res = Requests.GET(
            StudyReward,
            null,
            StudyRewardForm(loginResult.user_info.id, token).getParams(secret)
        )
        val deRes = this.resHandle(res, RewardResult::class.java)
        if (deRes.iSuccess()) {
            Socialize.dataBase.log(
                account,
                deRes.reward_data.coin,
                deRes.reward_data.total,
                DataBase.log_type.study, null
            )

            println("获取学习奖励: ${deRes.reward_data.coin} 当前酷币: ${deRes.reward_data.total}")
            return "获取学习奖励: ${deRes.reward_data.coin}"
        }
        return "获取学习失败"
    }

    fun rewardBySpell(): String {
        val res = Requests.GET(
            SpellReward,
            null,
            SpellRewardForm(loginResult.user_info.id, token).getParams(secret)
        )
        val deRes = this.resHandle(res, RewardResult::class.java)
        if (deRes.iSuccess()) {
            Socialize.dataBase.log(
                account,
                deRes.reward_data.coin,
                deRes.reward_data.total,
                DataBase.log_type.study, null
            )

            println("获取拼写奖励: ${deRes.reward_data.coin} 当前酷币: ${deRes.reward_data.total}")
            return "获取拼写奖励: ${deRes.reward_data.coin}"
        }
        return "获取拼写失败"
    }

    fun rewardByReview(): String {
        val res = Requests.GET(
            ReviewReward,
            null,
            ReviewRewardForm(loginResult.user_info.id, token).getParams(secret)
        )
        val deRes = this.resHandle(res, RewardResult::class.java)
        if (deRes.iSuccess()) {
            Socialize.dataBase.log(
                account,
                deRes.reward_data.coin,
                deRes.reward_data.total,
                DataBase.log_type.study, null
            )

            println("获取复习奖励: ${deRes.reward_data.coin} 当前酷币: ${deRes.reward_data.total}")
            return "获取复习奖励: ${deRes.reward_data.coin}"
        }
        return "获取复习奖励失败"
    }


    private fun <T> resHandle(res: Response, type: Class<T>): T {
        val json = Gson().fromJson(res.body!!.charStream(), V3Res::class.java)
        if (json.iSuccess()) {
            val deRes = V3Res.decodeDataBody(json.data_body, secret, SocializeConstants.CoolApiIV)
            return Gson().fromJson(deRes, type)
        } else {
            throw Exception("错误处理")
        }
    }


}

fun main(args: Array<String>) {
    val users = Socialize.dataBase.getUsers()
    users.forEach { a ->
        User.getInstance(a.account, a.password).run {
            try {
                try {
                    login()
                } catch (e: LoginFailure) {
                    val errcode = 0
                    Socialize.dataBase.log(
                        a.account,
                        errcode.toUInt(),
                        errcode.toUInt(),
                        DataBase.log_type.other,
                        e.message
                    )
                }
                val s = sign()
                val r1 = rewardByStudy()
                val r2 = rewardByReview()
                val r3 = rewardBySpell()
                PushPlus.send(TestConfig.push_token, "不背单词签到", "$s\n$r1\n$r2\n$r3")
                println("推送成功")
                println()
                Socialize.dataBase.updateLastUpdateTime(a.account)
            } catch (e: Exception) {
                println("发生错误: ${e.message}")
            }

        }
    }
}
