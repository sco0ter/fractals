package fractals

import javafx.application.Application
import javafx.application.Application.launch
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.image.WritableImage
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

fun main() {
    launch(FractalApp::class.java)
}

class FractalApp : Application() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun start(primaryStage: Stage) {

        val fractal = Fractal(Fractal.Function.MANDELBROT, 255, Complex(-1.5, 1.0), Complex(0.5, -1.0))
        val canvas = Canvas(400.0, 400.0)
        val channel = Channel<Fractal.Result>(canvas.height.toInt())
        GlobalScope.launch(Dispatchers.JavaFx) {
            fractal.applyToImage(canvas.width.toInt(), canvas.height.toInt()).collect {
                channel.send(it)
            }
            channel.close()
        }

        GlobalScope.launch(Dispatchers.JavaFx) {
            for (it in channel) {
                canvas.graphicsContext2D.pixelWriter.setArgb(it.x, it.y, it.color)
            }
        }

        val button = Button("Save")
        button.setOnAction {
            Files.newOutputStream(Path.of("fractal.png")).use {
                val image = WritableImage(canvas.width.toInt(), canvas.height.toInt())
                canvas.snapshot(null, image)
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", it)
            }
        }

        val vBox = VBox(20.0, canvas, button)
        vBox.padding = Insets(20.0, 20.0, 20.0, 20.0)
        primaryStage.scene = Scene(vBox)
        primaryStage.show()
        primaryStage.centerOnScreen()
    }
}