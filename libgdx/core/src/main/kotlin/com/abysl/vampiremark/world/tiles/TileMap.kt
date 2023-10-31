package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.coordinates.TileCoord

interface TileMap {

    fun getTileStack(coord: TileCoord): TileStack?

    fun setTileStack(coord: TileCoord, tileStack: TileStack)

    fun getTileStacks(coords: List<TileCoord>): Map<TileCoord, TileStack>

    fun setTileStacks(tileStacks: Map<TileCoord, TileStack>)
}
