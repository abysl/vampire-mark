package com.abysl.vampiremark.world.tiles

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString

@Serializable
data class Tile(
    val name: String,
    val aliases: List<String>,
    val properties: List<TileProperty>
){
    override fun toString(): String {
        return Json.encodeToString<Tile>(this)
    }
}
