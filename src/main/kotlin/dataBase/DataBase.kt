package dataBase

import java.io.Closeable
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

class DataBase(
    host: String,
    user: String,
    password: String,
    database: String
) : Closeable {
    enum class log_type {
        sign, study, spell, review, share,other
    }

    private var conn: Connection =
        DriverManager.getConnection("jdbc:mysql://$host/$database?user=$user&password=$password")
    private var stm: Statement

    init {
        conn.schema = "langeasy"
        stm = conn.createStatement()
        println("数据库连接成功")
    }


    fun getUsers(): ArrayList<User> {
        val query = """select * from user;"""
        val res = stm.executeQuery(query)
        val users = arrayListOf<User>()
        while (res.next()) {
            users.add(
                User(
                    res.getString("account"),
                    res.getString("password"),
                    res.getString("token")
                )
            )
        }
        return users
    }

    fun updateLastUpdateTime(account: String) {
        val query = """
            update langeasy.user
            set last_login=current_timestamp
            where account = '$account';
        """.trimIndent()

        stm.execute(query)
    }

    fun log(account: String, reward: UInt, coin: UInt, type: log_type, message: String?) {
        val msg = if (message == null)
            "null"
        else
            "'$message'"
        val query = """
            INSERT INTO log(account, reward, coin, type, message)
            VALUE ('$account', $reward, $coin, '${type.name}', ${msg}) 
        """.trimIndent()
        stm.execute(query)

    }

    fun test() {
        val sc = Scanner(System.`in`)
        while (true) {
            println("输入命令")
            try {
                val cmd = sc.nextLine()
                val end = stm.executeQuery(cmd)
                println(end)
            } catch (e: Exception) {
                println("出错啦 ${e.message}")
            }
        }

    }

    override fun close() {
        stm.close()
        conn.close()
        println("数据库连接关闭")
    }
}

fun main(array: Array<String>) {
}