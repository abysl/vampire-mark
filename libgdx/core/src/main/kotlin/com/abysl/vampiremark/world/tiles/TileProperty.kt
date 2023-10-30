package com.abysl.vampiremark.world.tiles
import com.abysl.vampiremark.world.spatial.Direction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class TileProperty {

    @Serializable
    @SerialName("Speed")
    data class Speed(val multiplier: Double) : TileProperty()

    @Serializable
    @SerialName("LevelShift")
    data class LevelShift(val ascend: Direction, val shift: Int) : TileProperty()

    @Serializable
    @SerialName("Teleport")
    data class Teleport(val x: Float, val y: Float, val z: Float) : TileProperty()

    @Serializable
    @SerialName("Filter")
    data class Filter(val conditions: Map<ConditionType, List<String>>) : TileProperty() {
        @Serializable
        enum class ConditionType {
            ALL, NONE, ONE
        }
    }

    @Serializable
    @SerialName("Damage")
    data class Damage(val percentage: Int) : TileProperty()

    @Serializable
    @SerialName("Poison")
    data class Poison(val stacks: Int) : TileProperty()

    @Serializable
    @SerialName("Collidable")
    data class Collidable(val isCollidable: Boolean) : TileProperty()

    @Serializable
    @SerialName("Wet")
    object Wet : TileProperty()
}
