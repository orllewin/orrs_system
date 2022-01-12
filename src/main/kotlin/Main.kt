import kotlin.system.exitProcess

// Try adding program arguments at Run/Debug configuration
fun main(args: Array<String>) {
    println("ORRS System")
    println("")
    val config = Config()
    if(!config.hasHost()) config.inputHost()
    if(!config.hasPort()) config.inputPort()
    if(!config.hasPassword()) config.inputPassword()
    if(!config.hasMount()) config.inputMount()

    if(config.isReady()){
        println("\nConfig is ready\n")
        Orrs(config).parseArgs(args)
    }else{
        println("Missing configuration parameter")
        exitProcess(0)
    }
}