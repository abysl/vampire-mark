package com.abysl.vampiremark.world.tiles

import kotlinx.serialization.Serializable

@Serializable
data class Tile(
    val name: String,
    val properties: List<TileProperty>
)
