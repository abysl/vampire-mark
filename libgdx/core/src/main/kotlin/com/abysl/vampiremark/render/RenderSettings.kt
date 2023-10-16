import com.abysl.vampiremark.world.spatial.conversions.tile
import com.abysl.vampiremark.math.GameVector

data class RenderSettings(
    val baseResolution: GameVector,
    val viewportResolution: GameVector,
) {
    companion object {
        val default_8 = RenderSettings(GameVector(160, 90), GameVector(20.tile, 11.25.tile))
        val default_16 = RenderSettings(GameVector(320, 180), GameVector(20.tile, 11.25.tile))
        val default_32 = RenderSettings(GameVector(640, 360), GameVector(20.tile, 11.25.tile))
    }
}
