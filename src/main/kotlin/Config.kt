import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Config {

    private val lines = mutableListOf<String>()

    companion object{
        private const val CONFIG_PATH = "~/etc/orrs_system/orrs.conf"
        private const val CONFIG_DIRECTORY = "~/etc/orrs_system/"
    }

    init {
       // Files.delete(Paths.get(CONFIG_PATH))
        when {
            configExists() -> load()
            else -> {
                val configCreated = createConfig()
                when {
                    configCreated -> println("Config file created successfully")
                    else -> println("Error creating config file")
                }
            }
        }
    }



    fun configExists(): Boolean = File(CONFIG_PATH).exists()

    fun createConfig(): Boolean {
        val createDirectory = createConfigDirectory()

        return when {
            !createDirectory -> false
            else -> {
                val configCreate = createConfigFile()

                when {
                    configCreate -> {
                        initialiseConfigFile()
                        load()
                    }
                }

                configCreate
            }
        }
    }

    private fun createConfigDirectory(): Boolean {
        return try {
            Files.createDirectories(Paths.get(CONFIG_DIRECTORY))
            true
        }catch (e: IOException){
            println("Error creating config directory: $e")
            false
        }
    }

    private fun createConfigFile(): Boolean{
        return try {
            Files.createFile(Paths.get(CONFIG_PATH))
            true
        }catch (e: IOException){
            println("Error creating config file: $e")
            false
        }
    }

    private fun initialiseConfigFile(){
        val sb = StringBuilder()
        sb.append("host:\n")
        sb.append("port:\n")
        sb.append("password:\n")
        sb.append("mount:\n")
        Files.write(Paths.get(CONFIG_PATH), sb.toString().toByteArray())
    }

    private fun load(){
        println("Config:")
        Files.lines(Paths.get(CONFIG_PATH)).forEach { line ->
            lines.add(line)
            println(line)
        }
    }

    private fun field(field: String): String?{
        val line = lines.firstOrNull { line ->
            line.startsWith(field)
        } ?: return null

        val value = line.substring(line.indexOf(":") +1, line.length).trim()
        return value
    }

    fun setField(field: String, value: String){
        val index = lines.indexOfFirst { line ->
            line.startsWith(field)
        }

        lines[index] = "$field:$value\n"
        writeConfig()
    }

    private fun writeConfig(){
        val sb = StringBuilder()
        lines.forEach { line ->
            sb.append(line)
            if(!line.endsWith("\n")) sb.append("\n")
        }
        Files.write(Paths.get(CONFIG_PATH), sb.toString().toByteArray())
    }

    fun hasHost(): Boolean {
        val host = field("host")
        return !host.isNullOrEmpty()
    }

    fun inputHost(){
        println("Enter host (orrs.live): ")
        val host = readLine()
        println("Setting host to $host")
        setField("host", "$host")
    }

    fun hasPort(): Boolean {
        val host = field("port")
        return !host.isNullOrBlank()
    }

    fun inputPort(){
        println("Enter port (8005): ")
        val port = readLine()
        println("Setting port to $port")
        setField("port", "$port")
    }

    fun hasMount(): Boolean {
        val mount = field("mount")
        return !mount.isNullOrBlank()
    }

    fun inputMount(){
        println("Enter mount (/): ")
        val mount = readLine()
        println("Setting mount to $mount")
        setField("mount", "$mount")
    }

    fun hasPassword(): Boolean {
        val password = field("password")
        return !password.isNullOrBlank()
    }

    fun inputPassword(){
        println("Enter password (password or email,password or email:password): ")
        val password = readLine()

        val length = password?.length ?: 0

        var mask = ""
        repeat(length){
            mask += "*"
        }

        println("Setting password to $mask")
        setField("password", "$password")
    }

    fun isReady(): Boolean{
        val hasHost = hasHost()
        if(!hasHost){
            println("Missing host")
        }
        val hasPort = hasPort()
        if(!hasPort){
            println("Missing port")
        }
        val hasPassword = hasPassword()
        if(!hasPassword){
            println("Missing password")
        }
        val hasMount = hasMount()
        if(!hasMount){
            println("Missing mount")
        }
        return hasHost && hasPort && hasPassword && hasMount
    }
}