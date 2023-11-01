package com.abysl.vampiremark.render

import com.abysl.vampiremark.settings.GameSettings
import com.abysl.vampiremark.settings.RenderSettings
import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.toPixelCoordinate
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.layer
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.tile
import com.abysl.vampiremark.world.tiles.ImmutableTileMap
import com.abysl.vampiremark.world.tiles.TileMapChunk
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
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

    val textureAtlas = TextureAtlas(Gdx.files.internal("atlas/world.atlas"))

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

        val interpolatedCameraPos =
            renderFrame.cameraPosition.position + renderFrame.cameraPosition.velocity * physicsDelta
        updateCameraPosition(interpolatedCameraPos)

        batch.projectionMatrix = camera.combined
        batch.use {
            renderTiles(renderFrame.tileMap)
            renderDrawables(renderFrame.drawables, physicsDelta)
        }
    }

    private fun updateCameraPosition(position: Vector2) {
        camera.position.set(position, 0f)
        camera.update()
    }

    private fun renderDrawables(drawables: List<Drawable>, physicsDelta: Float) {
        drawables.forEach { drawable ->
            val position =
                if (drawable.velocity != null) {
                    drawable.position + (drawable.velocity * physicsDelta)
                } else {
                    drawable.position
                }
            batch.draw(drawable.texture, position.x, position.y, 1.tile.toPixelFloat(), 1.tile.toPixelFloat())
        }
    }

    private fun renderTiles(tileMap: ImmutableTileMap) {
        val chunkCoordinate = camera.position.toPixelCoordinate().toChunkCoordinate()
        val chunk: TileMapChunk = tileMap.getChunk(chunkCoordinate)
        renderChunkTiles(chunk)
    }

    private fun renderChunkTiles(chunk: TileMapChunk) {
        chunk.tileStacks.forEach { (tileCoord, tileStack) ->
            val globalTileCoord = tileCoord.toGlobalCoordinate(chunk.coordinate)
            tileStack.tiles.forEach { tile ->
                val texture = textureAtlas.findRegion(tile.aliases.first())
                texture?.let {
                    batch.draw(
                        texture,
                        globalTileCoord.x.toPixelFloat(),
                        globalTileCoord.y.toPixelFloat(),
                        1.tile.toPixelFloat(),
                        1.tile.toPixelFloat()
                    )
                }
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

