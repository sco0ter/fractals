package fractals

import javafx.application.Application
import javafx.application.Application.launch
import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

fun main() {
    launch(FractalApp::class.java)
}

class FractalApp : Application() {
    override fun start(primaryStage: Stage) {

        val image = WritableImage(400, 400)
        val fractal = Fractal(Fractal.Function.MANDELBROT, 255, Complex(-1.5, 1.0), Complex(0.5, -1.0))

        fractal.applyToImage(image.width.toInt(), image.height.toInt()) { x: Int, y: Int, color: Int ->
            image.pixelWriter.setArgb(x, y, color)
        }

        val button = Button("Save")
        button.setOnAction {
            Files.newOutputStream(Path.of("fractal.png")).use {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", it)
            }
        }

        val vBox = VBox(20.0, ImageView(image), button)
        vBox.padding = Insets(20.0, 20.0, 20.0, 20.0)
        primaryStage.scene = Scene(vBox)
        primaryStage.show()
        primaryStage.centerOnScreen()
    }
}