package fractals

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import kotlin.math.abs

class OverlayCanvas(width: Double, height: Double) : StackPane() {

    private val canvas: Canvas

    val graphicsContext2D: GraphicsContext get() = canvas.graphicsContext2D

    init {
        canvas = Canvas(width, height)
        val overlay = Canvas()
        overlay.widthProperty().bind(canvas.widthProperty())
        overlay.heightProperty().bind(canvas.heightProperty())

        var startX = 0.0
        var startY = 0.0

        setOnMousePressed {
            startX = it.x
            startY = it.y
        }

        setOnMouseReleased {
            overlay.graphicsContext2D.clearRect(0.0, 0.0, overlay.width, overlay.height)
        }

        setOnMouseDragged {
            overlay.graphicsContext2D.clearRect(0.0, 0.0, overlay.width, overlay.height)
            val x = if (it.x > startX) startX else it.x
            val y = if (it.y > startY) startY else it.y

            overlay.graphicsContext2D.fillRect(x, y, abs(it.x - startX), abs(it.y - startY))
        }

        overlay.isMouseTransparent = true
        overlay.graphicsContext2D.fill = Color.color(0.9, 0.9, 0.9, 0.5)
        children.addAll(canvas, overlay)
    }
}