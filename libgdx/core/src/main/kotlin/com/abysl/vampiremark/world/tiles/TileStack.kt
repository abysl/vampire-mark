package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate

data class TileStack(
    val coord: TileCoordinate,
    val tiles: MutableList<TileMapTile> = mutableListOf()
) {

    fun getTilesOfType(type: String): List<TileMapTile> {
        return tiles.filter { it.name == type }
    }

    fun getTilesWithProperty(property: TileProperty): List<TileMapTile> {
        return tiles.filter { tile -> tile.properties.any { it == property } }
    }
}
