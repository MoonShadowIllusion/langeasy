package langEasyLexis.bean.data

data class Privileges(
    val bbvip: Privilege,
    val collins: Privilege,
    val wordroot: Privilege,
    val wordderive: Privilege,
    val xyjc: List<String>,
    val bbnote: Privilege,

    )
