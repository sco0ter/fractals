package fractals

class Fractal(private val function: (p: Complex) -> Function, private val maxIterations: Int, private val p1: Complex, private val p2: Complex) {

    fun applyToImage(width: Int, height: Int, callback: (x: Int, y: Int, color: Int) -> Unit) {
        val dx = (p2.re - p1.re) / (width - 1)
        val dy = (p2.im - p1.im) / (height - 1)
        var cx = p1.re
        var cy: Double

        for (x in 0 until width) {
            cx += dx
            cy = p2.im

            for (y in 0 until height) {
                cy -= dy
                val z = (calculate(Complex(cx, cy)) * 255).toInt()
                val r = z
                val g = z
                val b = z
                val argb = (255 shl 24) or (r shl 16) or (g shl 8) or b
                callback.invoke(x, y, argb)
            }
        }
    }

    private fun calculate(p: Complex): Double {
        // Get the fractal function, e.g. Mandelbrot or Julia, for the given complex number
        val f = function.invoke(p)

        // Start with z0
        var z = f.z0()
        for (n in 0 until maxIterations) {
            // z(n) = z(n-1) ^ 2 + c
            z = z.multiply(z).add(f.c())
            if (z.abs() >= 4.0) {
                return 1.0
            }
        }
        return 0.0
    }

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
        fun z0(): Complex

        /**
         * The constant c in the formula z(n+1) = z(n)^2 + c
         *
         * @return The constant c.
         */
        fun c(): Complex
    }
}

