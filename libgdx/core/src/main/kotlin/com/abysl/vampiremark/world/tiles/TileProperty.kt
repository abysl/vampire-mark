package com.abysl.vampiremark.world.tiles
import com.abysl.vampiremark.world.spatial.Direction
import kotlinx.serialization.Serializable

@Serializable
sealed class TileProperty {

    @Serializable
    data class SpeedProperty(val multiplier: Double) : TileProperty()

    @Serializable
    data class LevelShiftProperty(val ascend: Direction, val shift: Int) : TileProperty()

    @Serializable
    data class FilterProperty(val conditions: Map<ConditionType, List<String>>) : TileProperty() {
        @Serializable
        enum class ConditionType {
            ALL, NONE, ONE
        }
    }

    @Serializable
    data class Damage(val percentage: Int) : TileProperty()

    @Serializable
    data class Poison(val stacks: Int) : TileProperty()

    @Serializable
    data class Collidable(val isCollidable: Boolean) : TileProperty()

    @Serializable
    object WetProperty : TileProperty()
}
