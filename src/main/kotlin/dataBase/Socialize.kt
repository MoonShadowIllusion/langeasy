package dataBase

import langEasyLexis.TestConfig

object Socialize {
    val dataBase=DataBase(TestConfig.sql_host,TestConfig.sql_user,TestConfig.sql_pass,TestConfig.sql_database)
}