package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate

interface ImmutableTileMap {
    fun getTileStack(tileCoordinate: TileCoordinate): TileStack?
    fun getTileStacks(tileCoordinates: List<TileCoordinate>): Map<TileCoordinate, TileStack>
    fun getChunk(chunkCoordinate: ChunkCoordinate): TileMapChunk

}
interface TileMap: ImmutableTileMap {
    fun setTileStack(coordinate: TileCoordinate, tileStack: TileStack)

    fun setTileStacks(tileStacks: Map<TileCoordinate, TileStack>)

    fun setChunk(chunkCoordinate: ChunkCoordinate, chunk: TileMapChunk)
}
