import com.abysl.vampiremark.world.spatial.conversions.tile
import com.badlogic.gdx.math.Vector2

data class RenderSettings(
    val baseResolution: Vector2,
    val viewportResolution: Vector2,
) {
    companion object {
        val default_8 = RenderSettings(Vector2(160f, 90f), Vector2(20.tile.toFloat(), 11.25.tile.toFloat()))
        val default_16 = RenderSettings(Vector2(320f, 180f), Vector2(20.tile.toFloat(), 11.25.tile.toFloat()))
        val default_32 = RenderSettings(Vector2(640f, 360f), Vector2(20.tile.toFloat(), 11.25.tile.toFloat()))
    }
}
