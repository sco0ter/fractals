package fractals

class OrbitTrapAlgorithm : ColoringAlgorithm {

    private var distance = Double.MAX_VALUE

    override fun getColorValue(n: Int, maxIterations:Int, zAbs: Double, z: Complex, nColors: Int): Double {
        return distance
    }

    override fun increase(z: Complex) {

        val zMinusPointModulus: Double = z.subtract(Complex(0.0, 0.0)).abs()
        if (zMinusPointModulus < distance) {
            distance = zMinusPointModulus
        }
    }

    override fun isBailout(zAbs: Double, z:Complex): Boolean = false

    override fun reset() {
        distance = Double.MAX_VALUE
    }
}