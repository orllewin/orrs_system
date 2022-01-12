import kotlin.system.exitProcess

class Orrs(private val config: Config) {

    fun parseArgs(args: Array<String>){

        if(args.isEmpty()) {
            println(Help.defaultHelp())
            exitProcess(0)
        }else if(args.size == 2 && !args.first().startsWith("-")){
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
        }else {

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

    private fun parse(audio: String, timestamp: String){
        if(!config.isReady()){
            println("Missing config parameters, make sure everything is eup and try again")
            println(Help.defaultHelp())
            exitProcess(0)
        }

        println("Parsing audio: $audio and schedule: $timestamp")
    }
}