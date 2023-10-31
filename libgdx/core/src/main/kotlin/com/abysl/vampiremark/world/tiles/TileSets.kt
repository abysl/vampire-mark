package com.abysl.vampiremark.world.tiles

import kotlinx.serialization.Serializable

@Serializable
data class TileSets(
    val tileSets: List<TileSet>
){

    operator fun get(value: String): TileSet? = tileSets.firstOrNull { it.tileSetName == value }

    fun getTile(tileSetName: String, tileName: String): TileMapTile? = get(tileSetName)?.get(tileName)
}
