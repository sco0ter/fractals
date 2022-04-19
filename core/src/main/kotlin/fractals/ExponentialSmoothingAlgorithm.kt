package fractals

import kotlin.math.exp

/**
 * {@code e<sup>-|z|</sup>} smoothing.
 *
 * <p>This simply sums e over all iterations; as |z| increases, {@code e<sup>-|z|</sup>} approaches zero and further
 * iterations change the sum very little. Like normalized iteration counts, as long as the bailout radius is
 * sufficiently large, the precise value bears little impact on the resulting image.
 */
class ExponentialSmoothingAlgorithm : ColoringAlgorithm {

    var zSum: Double = 0.0

    override fun getColorValue(n: Int, zAbs: Double): Double {
        return zSum
    }

    override fun reset() {
        zSum = 0.0
    }

    override fun increase(z: Double) {
        zSum += exp(-z)
    }
}