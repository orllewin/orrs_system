import java.lang.Double.parseDouble

fun String.isAudioOrPlaylist(): Boolean{
    return this.lowercase().endsWith(".mp3") || this.lowercase().endsWith(".m3u")
}

//20210112_2107
fun String.isORRSTimestamp(): Boolean {
    return when {
        this.length == 13 && this.contains("_") -> {
            val segments = this.split("_")
            val left = segments.first()
            val right = segments[1]
            left.length == 8 && left.isNumber() && right.length == 4 && right.isNumber()
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