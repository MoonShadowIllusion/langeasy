package langEasyLexis.api

abstract class BaseApi {
    abstract var _path:String
    fun getPath():String{
        return "/v3/$_path/${System.currentTimeMillis()}"
    }

}