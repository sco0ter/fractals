package fractals

import javafx.application.Application
import javafx.application.Application.launch
import javafx.beans.InvalidationListener
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.WritableImage
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

fun main() {
    launch(FractalApp::class.java)
}

@OptIn(DelicateCoroutinesApi::class)
class FractalApp : Application() {
    private val canvas: OverlayCanvas = OverlayCanvas(400.0, 400.0)

    var p1 = Complex(-1.5, 1.0)
    var p2 = Complex(0.5, -1.0)
    private val colors = Fractal.createColors(12)

    override fun start(primaryStage: Stage) {

        drawFractal(p1, p2)

        val saveButton = Button("Save")
        saveButton.setOnAction {
            Files.newOutputStream(Path.of("fractal.png")).use {
                val image = WritableImage(canvas.width.toInt(), canvas.height.toInt())
                canvas.snapshot(null, image)
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", it)
            }
        }
        val resetButton = Button("Reset")
        resetButton.setOnAction {
            p1 = Complex(-1.5, 1.0)
            p2 = Complex(0.5, -1.0)
            drawFractal(p1, p2)
        }
        canvas.rectangle2DProperty.addListener(InvalidationListener {
            val rect2D = canvas.rectangle2DProperty.get()
            val ratioRe1 = rect2D.minX / canvas.width
            val ratioRe2 = (rect2D.minX + rect2D.width) / canvas.width
            val ratioIm1 = (rect2D.minY + rect2D.height) / canvas.height
            val ratioIm2 = rect2D.minY / canvas.height
            val re1 = p1.re + (p2.re - p1.re) * ratioRe1
            val im1 = p2.im + (p1.im - p2.im) * ratioIm1
            val re2 = p1.re + (p2.re - p1.re) * ratioRe2
            val im2 = p2.im + (p1.im - p2.im) * ratioIm2
            p1 = Complex(re1, im1)
            p2 = Complex(re2, im2)
            drawFractal(p1, p2)
        })
        val vBox = VBox(20.0, canvas, saveButton, resetButton)
        vBox.padding = Insets(20.0, 20.0, 20.0, 20.0)
        primaryStage.scene = Scene(vBox)
        primaryStage.show()
        primaryStage.centerOnScreen()
    }

    private fun drawFractal(p1: Complex, p2: Complex, coloringAlgorithm:ColoringAlgorithm = OrbitTrapAlgorithm()) {
        val fractal = Fractal(Fractal.Function.MANDELBROT, 215, colors, coloringAlgorithm, p1, p2)
        GlobalScope.launch(Dispatchers.JavaFx) {
            fractal.applyToImage(canvas.width.toInt(), canvas.height.toInt())
                    .buffer(canvas.height.toInt())
                    .collect {
                        canvas.graphicsContext2D.pixelWriter.setArgb(it.x, it.y, it.color)
                    }
        }
    }
}