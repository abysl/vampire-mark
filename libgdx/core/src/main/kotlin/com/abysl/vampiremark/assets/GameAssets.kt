package com.abysl.vampiremark.assets

import com.abysl.vampiremark.settings.GameSettings
import com.abysl.vampiremark.world.spatial.Direction
import com.abysl.vampiremark.world.tiles.Tile
import com.abysl.vampiremark.world.tiles.TileProperty
import com.abysl.vampiremark.world.tiles.TileSet
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver
import kotlinx.serialization.encodeToString
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

    val testTileSet = TileSet(
        "world",
        listOf(
            Tile(
                name = "Grass",
                aliases = listOf("floor_grass_a", "floor_grass_b", "floor_grass_c", "floor_grass_d", "floor_grass_e", "floor_grass_f"),
                properties = listOf()
            ),
            Tile(
                name = "Poison",
                aliases = listOf("acid", "acid_a1", "acid_a2"),
                properties = listOf(TileProperty.Poison(stacks = 1))
            ),
            Tile(
                name = "Lava",
                aliases = listOf("lava", "lava_a1", "lava_a2"),
                properties = listOf(TileProperty.Damage(percentage = 5))
            ),
            Tile(
                name = "StairDown",
                aliases = listOf("wall_cave_stair_down"),
                properties = listOf(TileProperty.LevelShiftProperty(ascend = Direction.SOUTH, shift = -1))
            ),
            Tile(
                name = "StairUp",
                aliases = listOf("wall_cave_stair_up"),
                properties = listOf(TileProperty.LevelShiftProperty(ascend = Direction.NORTH, shift = 1))
            ),
            Tile(
                name = "GrassTuft",
                aliases = listOf("grass_a", "grass_b", "grass_c", "grass_d", "grass_e"),
                properties = listOf()
            ),
            Tile(
                name = "Chest",
                aliases = listOf("chest"),
                properties = listOf(TileProperty.Collidable(isCollidable = true))
            ),
            Tile(
                name = "EmptyChest",
                aliases = listOf("chest_empty"),
                properties = listOf(TileProperty.Collidable(isCollidable = true))
            ),
            Tile(
                name = "WingedStatue",
                aliases = listOf("statue_winged"),
                properties = listOf(TileProperty.Collidable(isCollidable = true))
            ),
            Tile(
                name = "WarriorStatue",
                aliases = listOf("statue_warrior"),
                properties = listOf(TileProperty.Collidable(isCollidable = true))
            )
        )
    )

    init {
        KtxAsync.initiate()
        local.setLoader(suffix = ".tiles") {
            TileSetLoader(local.fileResolver, gameSettings.jsonSettings)
        }
        internal.setLoader(suffix = ".tiles") {
            TileSetLoader(local.fileResolver, gameSettings.jsonSettings)
        }

        val encoded = gameSettings.jsonSettings.encodeToString(testTileSet)
        Gdx.files.local("data/tilesets/world.tiles").writeString(encoded, false)
    }


}
