package com.abysl.vampiremark.ecs.artemis.component

import com.abysl.vampiremark.math.GameVector
import com.artemis.Component

class ArtemisVelocityComponent : Component() {
    var vec = GameVector()

    fun set(x: Int, y: Int, z: Byte): ArtemisVelocityComponent {
        vec.set(x, y, z)
        return this
    }
}
