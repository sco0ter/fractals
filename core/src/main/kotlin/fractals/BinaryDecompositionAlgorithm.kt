package fractals

/**
 * Uses the angle of z for coloring. Belongs to the escape angle family.
 *
 * Angles above the real axis are given one color, and zn with angles below the real axis are given another.
 *
 * This is of course a fundamentally discrete algorithm, but it provides a visual approximation to the binary form of
 * the field lines surrounding the fractal.
 *
 * @see ContinuousDecompositionAlgorithm
 */
class BinaryDecompositionAlgorithm : ColoringAlgorithm {
    override fun getColorValue(n: Int, maxIterations:Int, zAbs: Double, z: Complex, nColors: Int): Double {
        return (if (z.re / z.im > 0) 1.0 else 2.0)
    }
}