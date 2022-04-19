package fractals

import kotlin.math.log10

/**
 * A coloring algorithm that produces isocontours which are the same as the escape-time algorithm, if given the correct
 * parameters, yet it produces continuous values rather than discrete ones.
 *
 * <p>The formula for calculating the normalized iteration count is</p>
 * <pre>
 *     n + 1 − log(log zn) / log 2
 * </pre>
 */
class NormalizedIterationCountAlgorithm : ColoringAlgorithm {

    override fun getColorValue(n: Int, zAbs: Double): Double {
        return n + 1 - log10(log10(zAbs)) / log10(2.0)
    }
}