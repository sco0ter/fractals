package fractals

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.geometry.Rectangle2D
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import kotlin.math.abs

class OverlayCanvas(width: Double, height: Double) : StackPane() {

    private val rectangle2D: ReadOnlyObjectWrapper<Rectangle2D>

    private val canvas: Canvas

    private var tempRect:Rectangle2D = Rectangle2D(0.0, 0.0, 0.0, 0.0)

    val graphicsContext2D: GraphicsContext get() = canvas.graphicsContext2D

    val rectangle2DProperty: ReadOnlyObjectProperty<Rectangle2D> get() = rectangle2D.readOnlyProperty


    init {
        rectangle2D = ReadOnlyObjectWrapper(Rectangle2D(0.0, 0.0, width, height))
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
            rectangle2D.set(tempRect)
        }

        setOnMouseDragged {
            overlay.graphicsContext2D.clearRect(0.0, 0.0, overlay.width, overlay.height)

            var w = abs(it.x - startX)
            var h = abs(it.y - startY)
            val ratioCanvas = width / height
            val ratioOverlay = w / h
            val f = ratioOverlay / ratioCanvas
            if (f < 1) {
                h *= f
            } else {
                w /= f
            }
            val x = if (it.x > startX) startX else startX - w
            val y = if (it.y > startY) startY else startY - h
            tempRect = Rectangle2D(x, y, w, h)
            overlay.graphicsContext2D.fillRect(x, y, w, h)
        }

        overlay.isMouseTransparent = true
        overlay.graphicsContext2D.fill = Color.color(0.9, 0.9, 0.9, 0.5)
        children.addAll(canvas, overlay)
    }
}