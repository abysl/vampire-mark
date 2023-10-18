package com.abysl.vampiremark.ecs.artemis.delegates

import com.badlogic.gdx.math.Vector2
import kotlin.reflect.KProperty

class IntVecDelegate(private val vec: Vector2) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return when (property.name) {
            "x" -> vec.x.toInt()
            "y" -> vec.y.toInt()
            else -> throw IllegalArgumentException("Unsupported coordinate: ${property.name}")
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        when (property.name) {
            "x" -> vec.x = value.toFloat()
            "y" -> vec.y = value.toFloat()
            else -> throw IllegalArgumentException("Unsupported coordinate: ${property.name}")
        }
    }
}

