import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Orrs(private val config: Config) {

    fun parseArgs(args: Array<String>){

        when {
            args.isEmpty() -> {
                println(Help.defaultHelp())
                exitProcess(0)
            }
            args.size == 1 && args.first().startsWith("--") -> {
                if(args.first().lowercase() == "--deleteallcron") Cron().deleteAll()//Exits
            }
            args.size == 2 && !args.first().startsWith("-") -> {
                //Assume [mp3, timestamp] or [timestamp, mp3]
                val first = args.first()
                val second = args[1]

                when {
                    first.isAudioOrPlaylist() -> parse(first, second)
                    first.isORRSTimestamp() -> parse(second, first)
                    else -> {
                        println("Couldn't parse arguments: ${args.joinToString(" ")}")
                        exitProcess(0)
                    }
                }
            }
            else -> {

                println("Program arguments: ${args.joinToString(" ")}")

                val map = args.fold(Pair(emptyMap<String, List<String>>(), "")) { (map, lastKey), elem ->
                    if (elem.startsWith("-")) Pair(map + (elem to emptyList()), elem)
                    else Pair(map + (lastKey to map.getOrDefault(lastKey, emptyList()) + elem), lastKey)
                }.first

                if (args.isNotEmpty() && args.first() == "--reset") config.reset()//Exits

                if (map.containsKey("-host")) config.setField("host", map["-host"]?.first() ?: "")
                if (map.containsKey("-port")) config.setField("port", map["-port"]?.first() ?: "")
                if (map.containsKey("-password")) config.setField("password", map["-password"]?.first() ?: "")
                if (map.containsKey("-mount")) config.setField("mount", map["-mount"]?.first() ?: "")

                println(map)

                when {
                    map.containsKey("-timestamp") && map.containsKey("-file") ->
                        parse("${map["-file"]?.first()}", "${map["-timestamp"]?.first()}")
                    else -> {
                        println("No scheduling arguments found, exiting")
                        exitProcess(0)
                    }
                }
            }
        }
    }

    private fun parse(audio: String, timestamp: String){
        if(!config.isReady()){
            println("Missing config parameters:")
            if(!config.hasHost()) println("No host, set with -host")
            if(!config.hasPort()) println("No port, set with -port")
            if(!config.hasPassword()) println("No password, set with -password")
            if(!config.hasMount()) println("No mount point, set with -mount")
            println(Help.defaultHelp())
            exitProcess(0)
        }

        //todo - check audio is valid path

        println("Parsing audio: $audio and schedule: $timestamp")

        val liquidSoapFileContent = LiquidSoap().liqFileContent(
            audioPath = audio,
            host = config.host(),
            port = config.port(),
            password = config.password(),
            mount = config.mount()
        )

        val liqFilePath = createLiquidSoapFile(audio, timestamp, liquidSoapFileContent)

        when {
            timestamp.isORRSSinglePlayTimestamp() -> {
                //Use 'at'
            }
            timestamp.isORRSWeeklyTimestamp() -> {
                //Use cron
                val cronInterval = Time().weeklyORRSToCron(timestamp)
                Cron().createCronJob(cronInterval, liqFilePath)
            }
            timestamp.isORRSDailyTimestamp() -> {
                //Use cron
                val cronInterval = Time().dailyORRSToCron(timestamp)
                Cron().createCronJob(cronInterval, liqFilePath)
            }
        }
    }

    /**
     * Write the .liq file, make executable, return the path
     */
    private fun createLiquidSoapFile(audioPath: String, timestamp: String, liqContent: String): String{
        val liqFilePath = liquidSoapFilePath(audioPath, timestamp)
        val path = Paths.get(liqFilePath)
        Files.write(path, liqContent.toByteArray())
        Runtime.getRuntime().exec("chmod u+x $liqFilePath");
        return path.toFile().absolutePath
    }

    private fun liquidSoapFilePath(audioPath: String, timestamp: String): String{
        val path = audioPath.substring(0, audioPath.lastIndexOf("."))
        return "${path}_$timestamp.liq"
    }
}