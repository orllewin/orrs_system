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
            sb.append("\n")
            sb.append("Timestamp formats:")
            sb.append("Single play: yyyymmdd_hhmm eg. 20220113_2000")
            sb.append("Weekly recurring: ddd_hhmm where dd is first 3 letters of the day. eg. wed_1100")
            sb.append("Daily recurring: hhmm. eg. 2200 - audio plays every day at 10pm")
            return sb.toString()
        }
    }
}