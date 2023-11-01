package com.abysl.vampiremark.world.spatial.units

import com.abysl.vampiremark.world.spatial.units.UnitExtensions.pixel
import com.abysl.vampiremark.world.spatial.units.UnitExtensions.tile
import kotlinx.serialization.Serializable


const val CHUNK_SIZE = 32
const val TILE_SIZE = 16
@Serializable
sealed class SpatialUnit(val pixelValue: Int) {
    abstract val unitValue: Int

    fun toPixel(): Pixel = if(this is Pixel) this else Pixel(pixelValue)
    fun toTile(): Tile = if(this is Tile) this else Tile(pixelValue / TILE_SIZE)
    fun toChunk(): Chunk = if(this is Chunk) this else Chunk(pixelValue / (TILE_SIZE * CHUNK_SIZE))

    fun toUnitFloat() = unitValue.toFloat()
    fun toUnitDouble() = unitValue.toDouble()

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

    operator fun times(factor: SpatialUnit): Pixel {
        val resultPixelValue = this.pixelValue * factor.pixelValue
        return Pixel(resultPixelValue)
    }

    operator fun div(factor: SpatialUnit): Pixel {
        val resultPixelValue = this.pixelValue / factor.pixelValue
        return Pixel(resultPixelValue)
    }

    operator fun rem(factor: SpatialUnit): Pixel {
        val resultPixelValue = this.pixelValue % factor.pixelValue
        return Pixel(resultPixelValue)
    }

    operator fun unaryMinus(): Pixel {
        return Pixel(-this.pixelValue)
    }
}

@Serializable
data class Pixel(override val unitValue: Int) : SpatialUnit(unitValue)

@Serializable
data class Tile(override val unitValue: Int) : SpatialUnit(unitValue * TILE_SIZE)

@Serializable
data class Chunk(override val unitValue: Int) : SpatialUnit(unitValue * TILE_SIZE * CHUNK_SIZE)

@Serializable
data class Layer(val value: Int)

