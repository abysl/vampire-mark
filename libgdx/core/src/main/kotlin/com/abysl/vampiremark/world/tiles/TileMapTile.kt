package com.abysl.vampiremark.world.tiles

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class TileMapTile(
    val name: String,
    val aliases: List<String>,
    val properties: List<TileProperty>
){
    override fun toString(): String {
        return Json.encodeToString<TileMapTile>(this)
    }
}
