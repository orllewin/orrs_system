import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Cron {

    companion object{
        private const val CROND_DIRECTORY = "~/etc/cron.d/orrs/"

        fun logCronDirectory(){
            val cronDFiles = File(CROND_DIRECTORY).listFiles()
            cronDFiles.forEach { cronDFile ->
                println(">>>>>>> ${cronDFile.name}")
                val content = String(Files.readAllBytes(Paths.get(cronDFile.toURI())))
                println(">>>>>>>>>>>>>> $content")
            }
        }
    }

    fun deleteAll(){
        val cronDFiles = File(CROND_DIRECTORY).listFiles()
        cronDFiles.forEach { cronDFile ->
            Files.deleteIfExists(Paths.get(cronDFile.toURI()))
        }

        println("All ORRS cron jobs deleted")
        exitProcess(0)
    }

    fun createCronJob(cronInterval: String, liqFilePath: String){
        if(!orrsCronDirectoryReady()) createCronDirectory()
        val cronDPath = Paths.get("${CROND_DIRECTORY}${System.currentTimeMillis()}")
        Files.createFile(cronDPath)
        Files.write(cronDPath, "$cronInterval $liqFilePath".toByteArray())
    }

    private fun orrsCronDirectoryReady(): Boolean{
        val cronDDir = File(CROND_DIRECTORY)
        return cronDDir.exists()
    }

    private fun createCronDirectory(): Boolean {
        return try {
            Files.createDirectories(Paths.get(CROND_DIRECTORY))
            true
        }catch (e: IOException){
            println("Error creating cron.d orrs directory: $e")
            false
        }
    }
}