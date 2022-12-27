package fractals

import kotlin.math.exp

/**
 * `e ^ -|z|` smoothing.
 *
 * This simply sums e over all iterations; as |z| increases, `e ^ -|z|` approaches zero and further
 * iterations change the sum very little. Like normalized iteration counts, as long as the bailout radius is
 * sufficiently large, the precise value bears little impact on the resulting image.
 */
class ExponentialSmoothingAlgorithm : ColoringAlgorithm {

    var zSum: Double = 0.0

    override fun getColorValue(n: Int, maxIterations:Int, zAbs: Double, z:Complex, nColors:Int): Double {
        return zSum * 0.2
    }

    override fun reset() {
        zSum = 0.0
    }

    override fun increase(z: Complex) {
        zSum += exp(-z.abs())
    }
}