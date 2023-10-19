package com.abysl.vampiremark.ecs.artemis.component

import com.abysl.vampiremark.ecs.artemis.delegates.IntVecDelegate
import com.artemis.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

class ArtemisPositionComponent(x: Int = 0, y: Int = 0, var z: Byte = 0): Component() {
    val vec = Vector2(x.toFloat(), y.toFloat())

    var x by IntVecDelegate(vec)
    var y by IntVecDelegate(vec)

    fun set(x: Int = 0, y: Int = 0, z: Byte = 0): ArtemisPositionComponent {
        this.x = x
        this.y = y
        this.z = z
        return this
    }

    fun set(new: Vector2){
        vec.set(new)
    }
}
