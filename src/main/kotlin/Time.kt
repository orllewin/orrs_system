import java.text.SimpleDateFormat
import java.util.*

/**
 * Converts ORRS input into Cron intervals.
 * Cron has 5 fields: * * * * *
 * MIN: minute: 0 to 59
 * HOUR: hour: 0 to 23
 * DOM: day of month: 1 to 31
 * MON: month: 1 to 12
 * DOW: day of week: 0 to 6
 */
class Time {

    companion object{
        var sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")

        fun logNow(): String = sdf.format(Date())
    }

    /**
     * Parses an ORRS time and returns a Cron interval
     * @param input - time string in 24hr format, no punctuation. eg: 1330, 2200, 0800
     */
    fun dailyORRSToCron(input: String): String{
        if(!input.isORRSDailyTimestamp()) throw Exception("$input is not in correct form for an ORRS daily interval")

        val hour = input.substring(0, 2)
        val minutes = input.substring(2, 4)

        return "$minutes $hour * * *"
    }

    fun weeklyORRSToCron(input: String): String{
        if(!input.isORRSWeeklyTimestamp()) throw Exception("$input is not in correct form for an ORRS weekly interval")

        val segments = input.split("_")

        val day = segments.first()
        val time = segments[1]

        val hour = time.substring(0, 2)
        val minutes = time.substring(2, 4)

        return "$minutes $hour * * $day"
    }
}