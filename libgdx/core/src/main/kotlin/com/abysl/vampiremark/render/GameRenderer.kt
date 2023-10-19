package com.abysl.vampiremark.render

import com.abysl.vampiremark.settings.GameSettings
import com.abysl.vampiremark.settings.RenderSettings
import com.abysl.vampiremark.world.spatial.conversions.ftile
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import kotlin.math.min

class GameRenderer(
    private val gameSettings: MutableStateFlow<GameSettings>,
    private val batch: SpriteBatch = SpriteBatch(),
) : Disposable {

    private val camera: OrthographicCamera = OrthographicCamera()
    val renderSettings: RenderSettings
        get() = gameSettings.value.renderSettings

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
        val scale = min(baseScaleX, baseScaleY)

        // Adjust the viewport if the scaled resolution is different from the window size
        camera.viewportWidth = (width / scale)
        camera.viewportHeight = (height / scale)
        camera.update()
    }

    fun render(renderFrame: RenderFrame, physicsDelta: Float) {
        // Interpolate camera's position
        val interpolatedCameraPos = renderFrame.cameraPosition.position + renderFrame.cameraPosition.velocity * physicsDelta
        camera.position.set(interpolatedCameraPos, 0f)
        camera.update()

        batch.projectionMatrix = camera.combined
        batch.use {
            // Draw infinite checkerboard pattern
            val tileSize = 16f
            val texture1 = Texture(Gdx.files.internal("blue.png")) // Load your first texture
            val texture2 = Texture(Gdx.files.internal("rgchecker.png")) // Load your second texture

            val baseX = (camera.position.x - camera.viewportWidth / 2).toInt()
            val baseY = (camera.position.y - camera.viewportHeight / 2).toInt()
            val endX = (camera.position.x + camera.viewportWidth / 2).toInt()
            val endY = (camera.position.y + camera.viewportHeight / 2).toInt()

            val offsetStartX = baseX % tileSize.toInt()
            val offsetStartY = baseY % tileSize.toInt()

            for (x in (baseX - offsetStartX)..endX step tileSize.toInt()) {
                for (y in (baseY - offsetStartY)..endY step tileSize.toInt()) {
                    val texture = if ((x / tileSize.toInt() + y / tileSize.toInt()) % 2 == 0) texture1 else texture2
                    batch.draw(texture, x.toFloat(), y.toFloat(), tileSize, tileSize)
                }
            }



            // Render other drawables
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
