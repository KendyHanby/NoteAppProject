package com.cw.m_note

import android.text.format.DateUtils
import java.util.Calendar
import java.util.Date

fun main() {
    val d1 = Date(1693207774106)
    val d2 = Date(System.currentTimeMillis())
    val d3 = Date(d2.time - d1.time)

}