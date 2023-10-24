package com.abysl.vampiremark.ecs.components

import com.artemis.Component
import com.badlogic.gdx.graphics.Texture

class TextureComponent() : Component() {
    lateinit var texture: Texture

    fun set(texture: Texture): TextureComponent {
        this.texture = texture
        return this
    }
}
