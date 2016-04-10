package ru.litun.unitingtwist

/**
 * Created by Litun on 10.04.2016.
 */
class GraphPoint(val x: Int, val y: Int, val point: Point) : Comparable<GraphPoint> {
    override fun compareTo(other: GraphPoint): Int {
        if (x > other.x) return 1
        else if (x < other.x) return -1
        else if (y > other.y) return 1
        else if (y < other.y) return -1
        else return 0
    }

    var visited: Boolean = false
    var hasObject: Boolean = false

    fun distance(other: Point): Float =
            Math.sqrt (((other.x - point.x) * (other.x - point.x) +
                    (other.y - point.y) * (other.y - point.y)).toDouble()).toFloat()
}
