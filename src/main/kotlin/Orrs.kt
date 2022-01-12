import kotlin.system.exitProcess

class Orrs(val config: Config) {

    fun parseArgs(args: Array<String>){
        println("Now: ${Time.logNow()}")

        if(args.isEmpty()){
            println("Missing arguments")
            exitProcess(0)
        }else{
            println("Program arguments: ${args.joinToString(" ")}")

            val map = args.fold(Pair(emptyMap<String, List<String>>(), "")) { (map, lastKey), elem ->
                if (elem.startsWith("-"))  Pair(map + (elem to emptyList()), elem)
                else Pair(map + (lastKey to map.getOrDefault(lastKey, emptyList()) + elem), lastKey)
            }.first

            if(map.containsKey("-host")) config.setField("host", map["-host"]?.first() ?: "")
            if(map.containsKey("-port")) config.setField("port", map["-port"]?.first() ?: "")
            if(map.containsKey("-password")) config.setField("password", map["-password"]?.first() ?: "")
            if(map.containsKey("-mount")) config.setField("mount", map["-mount"]?.first() ?: "")



            println(map)
        }
    }
}