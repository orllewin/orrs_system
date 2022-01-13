import java.lang.Double.parseDouble

fun String.isAudioOrPlaylist(): Boolean{
    return this.lowercase().endsWith(".mp3") || this.lowercase().endsWith(".m3u")
}

//20210112_2107
fun String.isORRSTimestamp(): Boolean {
    return when {
        this.isORRSSinglePlayTimestamp() -> true
        this.isORRSDailyTimestamp() -> true
        this.isORRSWeeklyTimestamp() -> true
        else -> false
    }
}

fun String.isORRSSinglePlayTimestamp(): Boolean {
    return when {
        this.length == 13 && this.contains("_") -> {
            //Single play date and time: eg. 20220113_1800
            val segments = this.split("_")
            val left = segments.first()
            val right = segments[1]
            left.length == 8 && left.isNumber() && right.length == 4 && right.isNumber()
        }
        else -> false
    }
}

fun String.isORRSDailyTimestamp(): Boolean {
    return when {
        this.length == 4 && this.isNumber() -> {
            //Daily play at time: eg. 2200
            true
        }
        else -> false
    }
}

fun String.isORRSWeeklyTimestamp(): Boolean{
    return when {
        this.length == 8  && this.contains("_") -> {
            //Weekly play at time, SimpleDateFormats EEE, mon, tue, wed, thu, fri, sat, sun: eg. tue_1300
            val segments = this.split("_")
            val left = segments.first()
            val right = segments[1]
            left.length == 3 && !left.isNumber() && right.length == 4 && right.isNumber()
        }
        else -> false
    }
}

fun String.isNumber(): Boolean {
    try {
        val num = parseDouble(this)
        return true
    } catch (e: NumberFormatException) {
        return false
    }
}