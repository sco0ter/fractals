package fractals

/**
 * The escape-time algorithm is based on the number of iterations necessary to determine whether the orbit sequence
 * tends to infinity or not. It can be strictly demonstrated that when the orbit of any value of z0, z1, z2... zn
 * exceeds a border region R, it always diverges towards infinity.
 *
 * The minimum size and shape of R are different for each fractal type, of course. If the orbit sequence is stopped
 * as soon as zn is outside the border region R, then the coloring value for the escape-time algorithm is simply the
 * length of the sequence, that is, n.
 */
class EscapeTimeAlgorithm : ColoringAlgorithm {

    override fun getColorValue(n: Int, zAbs: Double, z:Complex, nColors:Int): Double {
        return n.toDouble() * 0.2
    }
}