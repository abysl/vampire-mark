package com.abysl.vampiremark.world.gen.levels

import com.abysl.vampiremark.world.gen.WorldGenerator
import com.abysl.vampiremark.world.spatial.SpatialConfig
import com.abysl.vampiremark.world.spatial.coordinates.ChunkCoordinate
import com.abysl.vampiremark.world.spatial.coordinates.TileCoordinate
import com.abysl.vampiremark.world.spatial.units.Tile
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.layer
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.tile
import com.abysl.vampiremark.world.tiles.TileMapChunk
import com.abysl.vampiremark.world.tiles.TileSet
import com.abysl.vampiremark.world.tiles.TileStack
import de.articdive.jnoise.JNoise
import kotlin.random.Random

class OverworldGenerator(
    private val tileSets: List<TileSet>,
    private val noiseScale: Double = 0.5,
    private val seed: Long = 22,
) : WorldGenerator {

    constructor(tileSet: TileSet, noiseScale: Double = 0.5, seed: Long = 22): this(listOf(tileSet), noiseScale, seed)

    private val tileSet = tileSets.first()

    private val noise: JNoise = JNoise.newBuilder()
        .superSimplex()
        .setSeed(seed)
        .build()

    override fun generateChunk(chunkCoordinate: ChunkCoordinate): TileMapChunk {
        val chunkSize = SpatialConfig.CHUNK_SIZE
        val tileSize = SpatialConfig.TILE_SIZE

        val chunk = TileMapChunk(chunkCoordinate)

        for (x in 0 until chunkSize) {
            for (y in 0 until chunkSize) {
                val tileStack = TileStack(TileCoordinate(x.tile, y.tile, 0.layer))
                val worldX = (chunkCoordinate.xChunk.value * chunkSize + x) * tileSize
                val worldY = (chunkCoordinate.yChunk.value * chunkSize + y) * tileSize
                val noiseValue =
                    noise.getNoise(worldX.toDouble() * noiseScale, worldY.toDouble() * noiseScale).toFloat()

                val tileCoordinate = TileCoordinate(
                    Tile(chunkCoordinate.xChunk.value * chunkSize + x),
                    Tile(chunkCoordinate.yChunk.value * chunkSize + y),
                    chunkCoordinate.zLayer
                )

                // Base terrain (grass) with a chance of other features
                var baseTile = tileSet["Grass"]

                // Lava pools
                if (noiseValue < -0.7 && Random.nextFloat() < 0.05) {
                    baseTile = tileSet["Lava"]
                }

                // Poison pools
                if (noiseValue > -0.7 && noiseValue < -0.2 && Random.nextFloat() < 0.05) {
                    baseTile = tileSet["Poison"]
                }

                if (baseTile != null) {
                    tileStack.tiles.add(baseTile)
                }

                // Random objects
                if (Random.nextFloat() < 0.02 && baseTile?.name == "Grass") {
                    val randomTileNames = listOf(
                        "StairDown",
                        "StairUp",
                        "GrassTuft",
                        "Chest",
                        "EmptyChest",
                        "WingedStatue",
                        "WarriorStatue"
                    )
                    val randomTileName = randomTileNames.random()
                    val randomTile = tileSet[randomTileName]
                    if (randomTile != null) {
                        tileStack.tiles.add(randomTile)
                    }
                }

                chunk.setTileStack(tileCoordinate, tileStack)
            }
        }
        return chunk
    }
}
