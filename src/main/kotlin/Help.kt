class Help {
    companion object{
        fun defaultHelp(): String {
            val sb = StringBuilder()
            sb.append("ORRS System helps automate the scheduling of broadcasts to an Icecast server using LiquidSoap\n")
            sb.append("\n")
            return sb.toString()
        }
    }
}