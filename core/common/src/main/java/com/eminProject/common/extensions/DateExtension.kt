package com.eminProject.common.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun dateExtension(isoDate: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(isoDate)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("tr"))
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        isoDate
    }
}