package com.abysl.vampiremark.render

import RenderSettings
import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.graphics.use
import kotlin.math.min
import kotlin.math.roundToInt

class GameRenderer(
    private val renderSettings: RenderSettings,
    private val batch: SpriteBatch = SpriteBatch(),
): Disposable {

    private val texture: Texture = Texture(Gdx.files.internal("archer.png"))
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

    fun render(renderFrame: RenderFrame, deltaTime: Float) {
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.use {
            renderFrame.drawables.forEach {
                it.draw(batch)
            }
            // Drawing the texture at (0,0) which is the center of the screen
            batch.draw(texture, -32f , -0f, 16f, 16f)
        }
    }

    override fun dispose() {
        texture.dispose()
        batch.dispose()
    }
}
