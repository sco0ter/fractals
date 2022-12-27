package fractals

import kotlin.math.round

/**
 * Uses the angle of z for coloring. Belongs to the escape angle family.
 *
 * @see BinaryDecompositionAlgorithm
 */
class GaussianIntegersAlgorithm : ColoringAlgorithm {
    override fun getColorValue(n: Int, maxIterations:Int, zAbs: Double, z: Complex, nColors: Int): Double {

        val gZRe: Double = round(z.re)
        val gZIm: Double = round(z.im)

        val dRe: Double = gZRe - z.re
        val dIm: Double = gZIm - z.im

        return Complex(dRe, dIm).abs()
    }
}