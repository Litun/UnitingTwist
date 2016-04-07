package ru.litun.unitingtwist

/**
 * Created by Litun on 07.04.2016.
 */
data class Point(var x: Float, var y: Float, var z: Float) {

    constructor(a: FloatArray) : this(a[0], a[1], a[2]) {
    }

    fun asArray(): FloatArray =
            floatArrayOf(x, y, z)

}