package com.abysl.vampiremark.world.spatial.units

import com.abysl.vampiremark.world.spatial.SpatialConfig
import kotlinx.serialization.Serializable

@Serializable
sealed class SpatialUnit(val pixelValue: Int) {
    abstract val value: Int

    fun toPixel(): Pixel = Pixel(pixelValue)
    fun toTile(): Tile = Tile(pixelValue / SpatialConfig.TILE_SIZE)
    fun toChunk(): Chunk = Chunk(pixelValue / (SpatialConfig.TILE_SIZE * SpatialConfig.CHUNK_SIZE))

    fun toUnitInt() = value
    fun toUnitFloat() = value.toFloat()
    fun toUnitDouble() = value.toDouble()

    fun toPixelInt() = pixelValue
    fun toPixelFloat() = pixelValue.toFloat()
    fun toPixelDouble() = pixelValue.toDouble()

    operator fun plus(other: SpatialUnit): Pixel {
        val resultPixelValue = this.pixelValue + other.pixelValue
        return Pixel(resultPixelValue)
    }

    operator fun minus(other: SpatialUnit): Pixel {
        val resultPixelValue = this.pixelValue - other.pixelValue
        return Pixel(resultPixelValue)
    }

    operator fun times(factor: Int): Pixel {
        val resultPixelValue = this.pixelValue * factor
        return Pixel(resultPixelValue)
    }

    operator fun div(factor: Int): Pixel {
        val resultPixelValue = this.pixelValue / factor
        return Pixel(resultPixelValue)
    }

    operator fun unaryMinus(): Pixel {
        return Pixel(-this.pixelValue)
    }

    operator fun rem(factor: Int): Pixel {
        val resultPixelValue = this.pixelValue % factor
        return Pixel(resultPixelValue)
    }
}

@Serializable
data class Pixel(private val pValue: Int) : SpatialUnit(pValue) {
    override val value: Int by lazy { pixelValue }
}

@Serializable
data class Tile(private val tileValue: Int) : SpatialUnit(tileValue * SpatialConfig.TILE_SIZE) {
    override val value: Int by lazy { pixelValue / SpatialConfig.TILE_SIZE }
}

@Serializable
data class Chunk(private val chunkValue: Int) : SpatialUnit(chunkValue * SpatialConfig.CHUNK_SIZE * SpatialConfig.TILE_SIZE) {
    override val value: Int by lazy { pixelValue / (SpatialConfig.TILE_SIZE * SpatialConfig.CHUNK_SIZE) }
}

@Serializable
data class Layer(val value: Int)

object SpatialConfig {
    const val TILE_SIZE = 16
    const val CHUNK_SIZE = 16
}
