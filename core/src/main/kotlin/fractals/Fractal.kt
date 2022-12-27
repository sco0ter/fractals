package fractals

import kotlinx.coroutines.flow.flow
import kotlin.math.floor
import kotlin.random.Random

class Fractal(private val function: (p: Complex) -> Function, private val maxIterations: Int,
              private val colors: List<Int>, private val coloringAlgorithm: ColoringAlgorithm, private val p1: Complex,
              private val p2: Complex) {

    class Result internal constructor(val x: Int, val y: Int, val color: Int)

    fun applyToImage(width: Int, height: Int) = flow {
        val dx = (p2.re - p1.re) / (width - 1)
        val dy = (p2.im - p1.im) / (height - 1)
        var cx = p1.re
        var cy: Double

        for (x in 0 until width) {
            cx += dx
            cy = p2.im

            for (y in 0 until height) {
                cy -= dy
                val z: Double = (calculate(Complex(cx, cy)))
                if (z == 0.0) {
                    emit(Result(x, y, 0xFF shl 24))
                } else {
                    val color1 = colors[floor(z).toInt() % colors.size]
                    val color2 = colors[(floor(z).toInt() + 1) % colors.size]
                    emit(Result(x, y, interpolate(color1, color2, z % 1)))
                }
            }
        }
    }

    private fun calculate(p: Complex): Double {
        // Get the fractal function, e.g. Mandelbrot or Julia, for the given complex number
        val f = function.invoke(p)
        // Start with z0
        var z = f.z0()
        coloringAlgorithm.reset()
        var zAbs = 0.0
        var i = 0

        for (n in 0 until maxIterations) {
            i++
            // z(n) = z(n-1) ^ 2 + c
            z = z.multiply(z).add(f.c())
            zAbs = z.abs()

            if (coloringAlgorithm.isBailout(zAbs, z)) {
                break
            }
            coloringAlgorithm.increase(z)
        }
        return coloringAlgorithm.getColorValue(i, maxIterations, zAbs, z, colors.size)
    }

    companion object {
        fun createColors(num: Int): List<Int> {

            val colors: MutableList<Int> = mutableListOf()
            for (i in 0 until num) {
                val r: Int = (Random.nextDouble() * 255).toInt()
                val g: Int = (Random.nextDouble() * 255).toInt()
                val b: Int = (Random.nextDouble() * 255).toInt()
                val argb = (255 shl 24) or (r shl 16) or (g shl 8) or b
                colors.add(argb)
            }
            return colors
        }

        private fun interpolate(argb1: Int, argb2: Int, f: Double): Int {
            val rf = 1 - f
            val r1 = argb1 and 0x00FF0000 shr 16
            val g1 = argb1 and 0x0000FF00 shr 8
            val b1 = argb1 and 0x000000FF
            val r2 = argb2 and 0x00FF0000 shr 16
            val g2 = argb2 and 0x0000FF00 shr 8
            val b2 = argb2 and 0x000000FF
            val r = (r1 * rf + r2 * f).toInt()
            val g = (g1 * rf + g2 * f).toInt()
            val b = (b1 * rf + b2 * f).toInt()
            return (255 shl 24) or (r shl 16) or (g shl 8) or b
        }
    }

    /**
     * The fractal function, e.g. for Mandebrot or Julia sets.
     *
     * The base formula is z(n+1) = z(n)^2 + c
     */
    /**
     * The fractal function, e.g. for Mandebrot or Julia sets.
     *
     * The base formula is z(n+1) = z(n)^2 + c
     */
    interface Function {
        companion object {
            val MANDELBROT: (p: Complex) -> Function = {
                object : Function {
                    override fun z0(): Complex {
                        return Complex(0.0, 0.0)
                    }

                    override fun c(): Complex {
                        return it
                    }
                }
            }

            val JULIASET: (p: Complex) -> Function = {
                object : Function {
                    override fun z0(): Complex {
                        return it
                    }

                    override fun c(): Complex {
                        return Complex(-0.7, 0.27015)
                    }
                }
            }
        }

        /**
         * The z(0) in the formula z(n+1) = z(n)^2 + c
         *
         * @return The z(0).
         */
        /**
         * The z(0) in the formula z(n+1) = z(n)^2 + c
         *
         * @return The z(0).
         */
        fun z0(): Complex

        /**
         * The constant c in the formula z(n+1) = z(n)^2 + c
         *
         * @return The constant c.
         */
        /**
         * The constant c in the formula z(n+1) = z(n)^2 + c
         *
         * @return The constant c.
         */
        fun c(): Complex
    }
}

