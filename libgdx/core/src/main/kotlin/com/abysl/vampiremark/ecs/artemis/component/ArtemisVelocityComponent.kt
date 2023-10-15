package com.abysl.vampiremark.ecs.artemis.component

import com.abysl.vampiremark.world.spatial.coordinates.PixelPoint
import com.artemis.Component

data class ArtemisVelocityComponent(
    var velocity: PixelPoint = PixelPoint.origin
): Component()
