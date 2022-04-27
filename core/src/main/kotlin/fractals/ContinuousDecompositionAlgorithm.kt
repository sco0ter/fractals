package fractals

import kotlin.math.PI
import kotlin.math.atan2

/**
 * Uses the angle of z for coloring. Belongs to the escape angle family.
 *
 * @see BinaryDecompositionAlgorithm
 */
class ContinuousDecompositionAlgorithm : ColoringAlgorithm {
    override fun getColorValue(n: Int, zAbs: Double, z: Complex, nColors: Int): Double {
        return (PI + atan2(z.re, z.im)) / (2 * PI) * nColors
    }
}