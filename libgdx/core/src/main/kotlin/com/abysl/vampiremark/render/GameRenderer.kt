package com.abysl.vampiremark.render

import com.abysl.vampiremark.settings.GameSettings
import com.abysl.vampiremark.settings.RenderSettings
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.tile
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
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
        val baseScaleX = width / renderSettings.baseResolution.x
        val baseScaleY = height / renderSettings.baseResolution.y
        val scale = min(baseScaleX, baseScaleY)

        camera.viewportWidth = (width / scale)
        camera.viewportHeight = (height / scale)
        camera.update()
    }

    fun render(renderFrame: RenderFrame, physicsDelta: Float) {
        clearScreen()

        val interpolatedCameraPos = renderFrame.cameraPosition.position + renderFrame.cameraPosition.velocity * physicsDelta
        camera.position.set(interpolatedCameraPos, 0f)
        camera.update()

        batch.projectionMatrix = camera.combined
        batch.use {
            renderFrame.drawables.forEach { drawable ->
                val position =
                    if(drawable.velocity != null) {
                        drawable.position + (drawable.velocity * physicsDelta)
                    }else {
                        drawable.position
                    }
                batch.draw(drawable.texture, position.x, position.y, 1.tile.toPixelFloat(), 1.tile.toPixelFloat())
            }
        }
    }

    private fun clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun dispose() {
        batch.dispose()
    }
}

