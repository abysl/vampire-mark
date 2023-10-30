package com.abysl.vampiremark.file

import com.abysl.vampiremark.world.tiles.TileSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

object TileSetManager {
    private const val TILE_SET_EXTENSION = ".json"

    suspend fun loadTileSet(name: String): TileSet = withContext(Dispatchers.IO) {
        val file = VampireMarkDataDir.getPathFor("$name$TILE_SET_EXTENSION")
        if (!file.exists()) throw FileNotFoundException("TileSet $name not found!")
        Json.decodeFromString(TileSet.serializer(), file.readString())
    }

    suspend fun saveTileSet(tileSet: TileSet) = withContext(Dispatchers.IO) {
        val file = VampireMarkDataDir.getPathFor("${tileSet.tileSetName}$TILE_SET_EXTENSION")
        file.writeString(Json.encodeToString(TileSet.serializer(), tileSet), false)
    }

    suspend fun exists(name: String): Boolean = withContext(Dispatchers.IO) {
        VampireMarkDataDir.getPathFor("$name$TILE_SET_EXTENSION").exists()
    }
}
