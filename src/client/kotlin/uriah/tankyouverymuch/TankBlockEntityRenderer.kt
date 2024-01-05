package uriah.tankyouverymuch

import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.minecraft.client.render.*
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Matrix4f
import net.minecraft.util.math.Vec3f
import uriah.tankyouverymuch.Blocks.Entity.TankBlockEntity
import java.util.*

@Environment(EnvType.CLIENT)
class TankBlockEntityRenderer: BlockEntityRenderer<TankBlockEntity> {
    override fun render(
        entity: TankBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        /*
        val stack = when(entity.fluidStorage.variant.fluid) {
            Fluids.WATER -> Items.WATER_BUCKET.defaultStack
            Fluids.LAVA -> Items.LAVA_BUCKET.defaultStack
            else -> Items.AIR.defaultStack
        }
        */

        /*
        val stack = entity.fluidStorage.variant.fluid.bucketItem.defaultStack

        val t = entity.world!!.time.toDouble()

        val offset = sin((t + tickDelta) / 8.0) / 8.0
        val ang = sin(t)
        val rotation = Quaternion.fromEulerXyzDegrees(Vec3f(0f, ang.toFloat(), 0f))

        matrices.push()
        matrices.translate(0.5, 1.25 + offset, 0.5)
        matrices.scale(0.5f, 0.5f, 0.5f)
        matrices.multiply(rotation)

        val lightAbove = WorldRenderer.getLightmapCoordinates(entity.world, entity.pos.up())

        MinecraftClient.getInstance().itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0)

        matrices.pop()
        */


        val fract = entity.fluidStorage.amount / entity.fluidStorage.capacity.toFloat()
        if (fract == 0f) {
            return
        }

        val fluidVariant = entity.fluidStorage.variant
        val sprite = FluidVariantRendering.getSprite(fluidVariant)
        val color = FluidVariantRendering.getColor(fluidVariant)
        val fluidLight = fluidVariant.fluid.defaultState.blockState.luminance
        val lightLevel = light.coerceAtLeast(fluidLight)

        if (sprite == null) {
            return
        }

        matrices.push()

        RenderSystem.setShaderTexture(0, sprite.atlas.id ?: PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)

        val EPSILON = 0.001f
        val MIN = 2.0f / 16.0f
        val MAX = 14.0f / 16.0f

        val x0 = EPSILON + MIN
        val y0 = EPSILON
        val z0 = EPSILON + MIN
        val x1 = MAX - EPSILON
        val y1 = (EPSILON + (1f - 0.002) * fract).toFloat()
        val z1 = MAX - EPSILON

        val sides = EnumSet.allOf(Direction::class.java)

        val consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucent())

        /*
        val minU = sprite?.getFrameU(0.0) ?: 0f
        val minV = sprite?.getFrameV(0.0) ?: 0f
        val maxU = sprite?.getFrameU(1.0) ?: 8f
        val maxV = sprite?.getFrameV(1.0) ?: 8f
        */

        val posMatrix = matrices.peek().positionMatrix

        // getFrameU/V returns the texture coordinates as an interpolation from 0 to 16. This makes it easy to scale the texture coordinates to match the size of the cube being rendered.
        // TODO: Does this scale correctly for different resolution textures?
        var minU = sprite.getFrameU(2.0)
        var minV = sprite.getFrameV(0.0)
        var maxU = sprite.getFrameU(14.0)
        var maxV = sprite.getFrameV((16.0f * fract).toDouble())

        // Draw a full cube using vertices from (x0, y0, z0, x1, y1, z1)
        /*
           5-------6
          /|      /|
         / |     / |
        1-------2  |
        |  |    |  |
        |  4----|--7
        | /     | /
        |/      |/
        0-------3
        */

        val p0 = Vec3f(x0, y0, z0)
        val p1 = Vec3f(x0, y1, z0)
        val p2 = Vec3f(x1, y1, z0)
        val p3 = Vec3f(x1, y0, z0)

        val p4 = Vec3f(x0, y0, z1)
        val p5 = Vec3f(x0, y1, z1)
        val p6 = Vec3f(x1, y1, z1)
        val p7 = Vec3f(x1, y0, z1)

        // North
        renderQuad(posMatrix, consumer, p0, p1, p2, p3, minU, minV, maxU, maxV, lightLevel, color)

        // South
        renderQuad(posMatrix, consumer, p7, p6, p5, p4, minU, minV, maxU, maxV, lightLevel, color)

        // West
        renderQuad(posMatrix, consumer, p4, p5, p1, p0, minU, minV, maxU, maxV, lightLevel, color)

        // East
        renderQuad(posMatrix, consumer, p3, p2, p6, p7, minU, minV, maxU, maxV, lightLevel, color)

        // Bottom
        minU = sprite.getFrameU(2.0)
        minV = sprite.getFrameV(2.0)
        maxU = sprite.getFrameU(14.0)
        maxV = sprite.getFrameV(14.0)
        renderQuad(posMatrix, consumer, p1, p5, p6, p2, minU, minV, maxU, maxV, lightLevel, color)

        // Top
        renderQuad(posMatrix, consumer, p4, p0, p3, p7, minU, minV, maxU, maxV, lightLevel, color)

        matrices.pop()
    }

    private fun renderQuad(
        matrix: Matrix4f,
        vertices: VertexConsumer,
        p1: Vec3f,
        p2: Vec3f,
        p3: Vec3f,
        p4: Vec3f,
        minU: Float = 0f,
        minV: Float = 0f,
        maxU: Float = 1f,
        maxV: Float = 1f,
        light: Int = 0,
        color: Int = 0xFFFFFFFF.toInt()
    ) {
        vertices.vertex(matrix, p1.x, p1.y, p1.z).color(color).texture(minU, minV).light(light).normal(0f, 1f, 0f).next()
        vertices.vertex(matrix, p2.x, p2.y, p2.z).color(color).texture(minU, maxV).light(light).normal(0f, 1f, 0f).next()
        vertices.vertex(matrix, p3.x, p3.y, p3.z).color(color).texture(maxU, maxV).light(light).normal(0f, 1f, 0f).next()
        vertices.vertex(matrix, p4.x, p4.y, p4.z).color(color).texture(maxU, minV).light(light).normal(0f, 1f, 0f).next()
    }
}