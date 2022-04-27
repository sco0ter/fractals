package fractals

import kotlin.math.log10

/**
 * A coloring algorithm that produces isocontours which are the same as the escape-time algorithm, if given the correct
 * parameters, yet it produces continuous values rather than discrete ones.
 *
 * The formula for calculating the normalized iteration count is
 *
 * ```
 * n + 1 − log(log(zn)) / log(2)
 * ```
 */
class NormalizedIterationCountAlgorithm : ColoringAlgorithm {

    override fun getColorValue(n: Int, zAbs: Double, z:Complex, nColors:Int): Double {
        return (n + 1 - log10(log10(zAbs)) / log10(2.0)) * 0.2
    }
}