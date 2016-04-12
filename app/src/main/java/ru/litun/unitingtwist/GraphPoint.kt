package ru.litun.unitingtwist

/**
 * Created by Litun on 10.04.2016.
 */
class GraphPoint(val x: Int, val y: Int, val point: Point) : Comparable<GraphPoint> {
    override fun compareTo(other: GraphPoint) =
            if (x > other.x) 1
            else if (x < other.x) -1
            else if (y > other.y) 1
            else if (y < other.y) -1
            else 0


    var visited: Boolean = false
    var hexObject: GameHexagon? = null

    fun distance(other: Point): Float =
            Math.sqrt (((other.x - point.x) * (other.x - point.x) +
                    (other.y - point.y) * (other.y - point.y)).toDouble()).toFloat()
}
