package com.abysl.vampiremark.world.tiles

import com.abysl.vampiremark.world.spatial.Direction
import kotlinx.serialization.*

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
    object WetProperty : TileProperty()
}
