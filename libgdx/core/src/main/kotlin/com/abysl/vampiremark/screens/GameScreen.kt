package com.abysl.vampiremark.screens

import com.abysl.vampiremark.assets.GameAssets
import com.abysl.vampiremark.assets.TileSetLoader
import com.abysl.vampiremark.world.GameWorld
import com.abysl.vampiremark.render.*
import com.abysl.vampiremark.settings.GameSettings
import com.abysl.vampiremark.world.gen.levels.OverworldGenerator
import com.abysl.vampiremark.world.tiles.ChunkedTileMap
import com.abysl.vampiremark.world.tiles.TileMap
import com.abysl.vampiremark.world.tiles.TileMapChunk
import com.abysl.vampiremark.world.tiles.TileSet
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver
import kotlinx.coroutines.flow.MutableStateFlow
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.newAsyncContext

class GameScreen : KtxScreen {
    val settings: MutableStateFlow<GameSettings> = MutableStateFlow(GameSettings())

    protected var physicsDelta = 0f
    var currentRenderFrame: RenderFrame? = null

    private val renderer = GameRenderer(settings)
    private val assets = GameAssets(settings.value)
    private val tilemap: TileMap = ChunkedTileMap(OverworldGenerator(assets.local.loadSync<TileSet>("data/tilesets/world.tiles")))
    private val world = GameWorld(tilemap)
    private val ecsFrameAdapter = EcsFrameAdapter(world)

    init {
        assets.local.loadSync<TileSet>("data/tilesets/world.tiles").tiles.forEach(::println)
    }


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        renderer.resize(width, height)
    }

    override fun render(delta: Float) {
        physicsDelta += delta
        val tickRate = settings.value.physicsSettings.tickRate
        while (physicsDelta >= tickRate) {
            world.update(tickRate)
            currentRenderFrame = ecsFrameAdapter.getFrame()
            physicsDelta -= tickRate
        }

        currentRenderFrame?.let {
            renderer.render(it, physicsDelta)
        }
    }

    override fun dispose() {
        renderer.dispose()
    }
}
