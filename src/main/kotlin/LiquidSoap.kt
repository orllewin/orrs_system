/**
 * A simple template provider for a LiquidSoap .liq file
 */
class LiquidSoap {

    companion object{
        const val TEMPLATE = "#!/usr/bin/liquidsoap\n" +
                "\n" +
                "show = once(single(\"__AUDIOFILE__\"))\n" +
                "\n" +
                "output.icecast(%vorbis,\n" +
                "  host = \"__HOST__\", \n" +
                "  port = __PORT__,\n" +
                "  password = \"__PASSWORD__\", \n" +
                "  mount = \"__MOUNT__\",\n" +
                "  on_stop = shutdown,\n" +
                "  fallible = true,\n" +
                "  show)"
    }

    fun liqFileContent(audioPath: String, host: String, port: String, password: String, mount: String): String{
        var liq = TEMPLATE
        liq = liq.replace("__AUDIOFILE__", audioPath)
        liq = liq.replace("__HOST__", host)
        liq = liq.replace("__PORT__", port)
        liq = liq.replace("__PASSWORD__", password)
        liq = liq.replace("__MOUNT__", mount)

        return liq
    }
}