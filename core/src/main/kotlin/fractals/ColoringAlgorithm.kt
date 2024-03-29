package fractals

/**
 * A coloring algorithm to apply to a single pixel.
 *
 * Every dynamical system produces a sequence of values z0, z1, z2... zn.
 * Fractal images are created by producing one of these sequences for each pixel in the image;
 * the coloring algorithm is what interprets this sequence to produce a final color.
 *
 * Typically, the coloring algorithm produces a single value for each pixel.
 * Since color is a three-dimensional space, this one-dimensional value must be expanded to produce a color image.
 * The common method is to create a palette, a sequence of 3D color values; these are connected end-to-end and the
 * coloring algorithm value is then used as a position along this multi-segmented line (the gradient).
 * If the last palette color is connected to the first, a closed, segmented loop is formed and any real value from the
 * coloring algorithm can be mapped to a defined color in the gradient.
 * This is similar to the pseudo-color renderings often used for infrared imaging.
 * Gradients are normally linearly interpolated in RGB space (Red, Green, Blue), but they can also be interpolated in
 * HSL space (Hue, Saturation, Lightness) and interpolated with spline curves instead of straight line segments.
 */
interface ColoringAlgorithm {

    /**
     * Gets the color value.
     *
     * @param n The iteration count
     * @param maxIterations The max iterations
     * @param zAbs The absolute value of z, i.e. the distance.
     * @param z The complex number z.
     * @param nColors The number of colors.
     * @return
     */
    fun getColorValue(n: Int, maxIterations:Int, zAbs: Double, z: Complex, nColors: Int): Double

    fun reset() {
    }

    fun increase(z: Complex) {
    }

    fun isBailout(zAbs: Double, z: Complex): Boolean = zAbs >= 16
}