import java.text.SimpleDateFormat
import java.util.*


class Time {

    companion object{
        var sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")

        fun logNow(): String = sdf.format(Date())
    }

    fun parse(input: String){

    }
}