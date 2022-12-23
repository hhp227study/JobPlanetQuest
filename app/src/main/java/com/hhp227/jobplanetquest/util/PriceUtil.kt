package com.hhp227.jobplanetquest.util

import java.text.DecimalFormat

object PriceUtil {
    fun getPrice(price: Int): String {
        val formatter = DecimalFormat("###,###")
        return "${formatter.format(price)}"
    }
}