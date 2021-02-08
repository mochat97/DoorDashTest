package com.mshaw.doordashtest.util.extensions

import kotlin.math.roundToInt

fun Double.round(toNearest: Double): Double {
    return (this / toNearest).roundToInt().toDouble() * toNearest
}