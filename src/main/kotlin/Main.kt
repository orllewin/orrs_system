import kotlin.system.exitProcess

fun main(args: Array<String>) {
    println("ORRS System")
    println("")
    Orrs(Config()).parseArgs(args)
}