class Help {
    companion object{
        fun defaultHelp(): String {
            val sb = StringBuilder()
            sb.append("ORRS System helps automate the scheduling of broadcasts to an Icecast server using LiquidSoap\n")
            sb.append("\n")
            sb.append("Usage:\n")
            sb.append("Set Icecast params:\n")
            sb.append("orrs_system -host orrs.live -port 8005 -password Hello123 -mount /mountPoint\n")
            sb.append("\n")
            sb.append("Schedule an audio file:\n")
            sb.append("orrs_system -timestamp 20220112_1800 -file show.mp3\n")
            sb.append("orrs_system 20220112_1800 show.mp3\n")
            sb.append("orrs_system show.mp3 20220112_1800\n")
            return sb.toString()
        }
    }
}