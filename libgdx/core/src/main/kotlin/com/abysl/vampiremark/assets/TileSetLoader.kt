package com.abysl.vampiremark.assets

import com.abysl.vampiremark.assets.TileSetLoader.*
import com.abysl.vampiremark.world.tiles.TileSet
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import kotlinx.serialization.json.Json

class TileSetLoader(resolver: FileHandleResolver, val json: Json) : AsynchronousAssetLoader<TileSet, TileSetParameter>(resolver) {

    private var tileSet: TileSet? = null

    class TileSetParameter : AssetLoaderParameters<TileSet>()

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: TileSetParameter?) {
        val jsonString = file.readString()
        tileSet = json.decodeFromString<TileSet>(jsonString)
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: TileSetParameter?): TileSet? {
        val tileSet = this.tileSet
        this.tileSet = null
        return tileSet
    }

    override fun getDependencies(fileName: String?, file: FileHandle?, parameter: TileSetParameter?): Array<AssetDescriptor<Any>>? {
        return null
    }
}
