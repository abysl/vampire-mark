package com.abysl.vampiremark.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable

class TileSet(private val atlasPath: String) : Disposable {
    val tiles = mutableMapOf<String, Tile>()
    val atlas = TextureAtlas(atlasPath)

    fun addTile(name: String, tile: Tile) {
        tiles[name] = tile
    }

    fun getTile(name: String): Tile? = tiles[name]
    fun getTexture(name: String): TextureRegion? = atlas.findRegion(name)

    override fun dispose() {
        atlas.dispose()
    }
}
