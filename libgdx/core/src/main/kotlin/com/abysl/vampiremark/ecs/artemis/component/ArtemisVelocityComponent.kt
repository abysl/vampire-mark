package com.abysl.vampiremark.ecs.artemis.component

import com.artemis.Component

data class ArtemisVelocityComponent(
    var x: Float = 0f,
    var y: Float = 0f
): Component()
