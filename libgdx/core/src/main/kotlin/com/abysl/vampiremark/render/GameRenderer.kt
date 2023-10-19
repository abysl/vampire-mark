package com.abysl.vampiremark.render

import com.abysl.vampiremark.settings.RenderSettings
import com.abysl.vampiremark.world.spatial.conversions.ftile
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import kotlin.math.min

class GameRenderer(
    private val renderSettings: RenderSettings,
    private val batch: SpriteBatch = SpriteBatch(),
) : Disposable {

    private val camera: OrthographicCamera = OrthographicCamera()

    init {
        // Set the initial position of the camera to the center of the game world.
        camera.position.set(0f, 0f, 0f)
        camera.viewportWidth = renderSettings.baseResolution.x
        camera.viewportHeight = renderSettings.baseResolution.y
    }

    fun resize(width: Int, height: Int) {
        // Calculate the scaling factor
        val baseScaleX = width / renderSettings.baseResolution.x
        val baseScaleY = height / renderSettings.baseResolution.y
        val scale = min(baseScaleX, baseScaleY).toFloat()

        // Adjust the viewport if the scaled resolution is different from the window size
        camera.viewportWidth = (width / scale)
        camera.viewportHeight = (height / scale)
        camera.update()
    }

    fun render(renderFrame: RenderFrame, physicsDelta: Float) {
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.use {
            renderFrame.drawables.forEach { drawable ->
                val position =
                    if (drawable.velocity == null)
                        drawable.position
                    else
                        drawable.position + (drawable.velocity * physicsDelta)

                batch.draw(drawable.texture, position.x, position.y, 1.ftile, 1.ftile)
            }
        }
    }

    override fun dispose() {
        batch.dispose()
    }
}
