package push

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

object PushPlus {
    val client = OkHttpClient.Builder().build()

    data class PushPlusResult(
        val code:Int,
        val data:String,
        val msg:String
    )
    fun send(token: String, content: String, title: String?) {
        val body = FormBody.Builder()
            .add("token", token)
            .add("content", content)

        title?.run { body.add("title", title) }

        val req = Request.Builder()
            .url("http://www.pushplus.plus/send")
            .addHeader("Content-Type", "application/json")
            .post(body.build())
            .build()
        val res = client.newCall(req).execute()

        val js = Gson().fromJson(res.body!!.charStream(), PushPlusResult::class.java)
        if (js.code==200){
            println("推送成功 流水号: ${js.data} 消息: ${js.msg}")
        }else
            println("推送失败 消息: ${js.msg}")
    }

}