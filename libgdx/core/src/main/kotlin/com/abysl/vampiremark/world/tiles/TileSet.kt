package com.abysl.vampiremark.world.tiles

import kotlinx.serialization.Serializable
import ktx.log.*

@Serializable
data class TileSet(
    val tileSetName: String,
    val tiles: List<TileMapTile>
){
    init {
        checkTileNameCollisions()
    }

    operator fun get(name: String): TileMapTile? = tiles.firstOrNull { it.name == name }

    private fun checkTileNameCollisions() {
        val seenNames = HashSet<String>()
        for (tile in tiles) {
            if (!seenNames.add(tile.name)) {
                error("Error") { "Tile name collision found: ${tile.name} in $tileSetName" }
            }
        }
    }
}
