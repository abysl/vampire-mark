package com.abysl.vampiremark.assets

import com.abysl.vampiremark.settings.GameSettings
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.async.newAsyncContext

class GameAssets(gameSettings: GameSettings) {
    val local = AssetStorage(
        asyncContext = newAsyncContext(2),
        fileResolver = LocalFileHandleResolver(),
    )

    val internal = AssetStorage(
        asyncContext = newAsyncContext(2),
    )

    init {
        KtxAsync.initiate()
        local.setLoader(suffix = ".tiles") {
            TileSetLoader(local.fileResolver, gameSettings.jsonSettings)
        }
        internal.setLoader(suffix = ".tiles") {
            TileSetLoader(local.fileResolver, gameSettings.jsonSettings)
        }
    }
}
