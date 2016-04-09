package ru.litun.unitingtwist

import kotlin.reflect.KProperty

/**
 * Created by Litun on 07.04.2016.
 */
data class Point(val defaultX: Float, val defaultY: Float, val defaultZ: Float) {

    var x: Float = defaultX
    var y: Float = defaultY
    var z: Float = defaultZ

    constructor(a: FloatArray) : this(a[0], a[1], a[2])

    fun asArray(): FloatArray =
            floatArrayOf(x, y, z)

}