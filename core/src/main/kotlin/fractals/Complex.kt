package fractals

import kotlin.math.hypot

/**
 * Simple complex number implementation with only a few methods required for fractal computation.
 */
data class Complex(val re: Double, val im: Double) {

    fun add(z: Complex): Complex {
        return Complex(re + z.re, im + z.im)
    }

    fun multiply(z: Complex): Complex {
        val r: Double = re * z.re - im * z.im
        val i: Double = re * z.im + im * z.re
        return Complex(r, i)
    }

    fun abs(): Double {
        return hypot(re, im)
    }

    override fun toString(): String {
        if (im == 0.0) {
            return re.toString()
        }
        if (re == 0.0) {
            return im.toString() + 'i'
        }
        return if (im < 0) re.toString() + " - " + -im + 'i' else re.toString() + " + " + im + 'i'
    }
}
