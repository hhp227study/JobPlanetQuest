package com.hhp227.jobplanetquest.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    fun getDate(dateString: String): Date? {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            formatter.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun Date.toDateString(): String {
        val formatter = SimpleDateFormat("YYYY.mm.dd")
        return formatter.format(this)
    }

    fun getParse(dateString: String): String { // detailscreen으로 넘어가면서 jsonparsing이 잘못되어 'T' 에 ' 가 사라져서 대응
        return dateString.split("-").let { (x, y, z) -> "$x.$y.${z.take(2)}" }
    }
}