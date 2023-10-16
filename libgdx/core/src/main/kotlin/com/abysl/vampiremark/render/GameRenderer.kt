package com.abysl.vampiremark.render

import RenderSettings
import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.graphics.use
import kotlin.math.roundToInt

class GameRenderer(
    private var renderSettings: RenderSettings,
    private val batch: SpriteBatch = SpriteBatch(),
): Disposable {

    private val camera = OrthographicCamera()
    private lateinit var viewport: Viewport

    init {
//        updateViewport(PixelPoint(Gdx.graphics.width.pixel, Gdx.graphics.height.pixel))
    }

    fun updateRenderSettings(newRenderSettings: RenderSettings) {
        renderSettings = newRenderSettings
//        updateViewport(PixelPoint(Gdx.graphics.width.pixel, Gdx.graphics.height.pixel))
    }

   /* private fun updateViewport(screenDimensions: PixelPoint) {
        val scaleFactor = calculateScaleFactor(screenDimensions)
        camera.setToOrtho(false, (renderSettings.viewportResolution.x * scaleFactor).toFloat, (renderSettings.viewportResolution.y * scaleFactor).toFloat)
        viewport = ScalingViewport(Scaling.none, camera.viewportWidth, camera.viewportHeight, camera)
    }

    private fun calculateScaleFactor(screenDimensions: PixelPoint): Int {
        val xScaleFactor = (screenDimensions.x.toFloat / SpatialConfig.TILE_SIZE).roundToInt()
        val yScaleFactor = (screenDimensions.y.toFloat / SpatialConfig.TILE_SIZE).roundToInt()

        val xArea = (renderSettings.baseResolution * xScaleFactor).area
        val yArea = (renderSettings.baseResolution * yScaleFactor).area

        val xDiff = (renderSettings.viewportResolution.area - xArea).abs().toInt
        val yDiff = (renderSettings.viewportResolution.area - yArea).abs().toInt

        return if (xDiff < yDiff) xScaleFactor else yScaleFactor
    }

    fun resize(width: Int, height: Int) {
        updateViewport(PixelPoint(width.pixel, height.pixel))
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
    }*/

    fun render(renderFrame: RenderFrame) {
        batch.use { batch ->
            for (drawable in renderFrame.drawables) {
//                batch.draw(drawable.texture, drawable.position.x.toFloat, drawable.position.y.toFloat)
            }
        }
    }

    override fun dispose() {
        batch.dispose()
    }
}
