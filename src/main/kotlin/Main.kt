import kotlin.system.exitProcess

fun main(args: Array<String>) {
    println("ORRS System")
    Cron.logCronDirectory()
    println("")
    Orrs(Config()).parseArgs(args)
}