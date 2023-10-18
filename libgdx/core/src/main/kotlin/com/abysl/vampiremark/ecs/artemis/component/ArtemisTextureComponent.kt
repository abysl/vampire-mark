package com.abysl.vampiremark.ecs.artemis.component

import com.artemis.Component
import com.badlogic.gdx.graphics.Texture

class ArtemisTextureComponent() : Component() {
    lateinit var texture: Texture

    fun set(texture: Texture): ArtemisTextureComponent {
        this.texture = texture
        return this
    }
}
