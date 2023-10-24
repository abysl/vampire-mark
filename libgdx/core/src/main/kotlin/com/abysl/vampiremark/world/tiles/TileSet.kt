package com.abysl.vampiremark.world.tiles

import kotlinx.serialization.Serializable

@Serializable
data class TileSet(
    val tileSetName: String,
    val tiles: List<Tile>
)
