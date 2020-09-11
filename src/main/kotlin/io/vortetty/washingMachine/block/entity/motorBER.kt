package io.vortetty.washingMachine.block.entity

import io.vortetty.washingMachine.block.motorBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayers
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Quaternion
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class motorBER(dispatcher: BlockEntityRenderDispatcher): BlockEntityRenderer<motorBlockEntity>(dispatcher){
    var manager = MinecraftClient.getInstance().getBlockRenderManager()

    fun getLight(pos: BlockPos, entity: BlockEntity): Int {
        return WorldRenderer.getLightmapCoordinates(entity.getWorld(), pos)
    }

    fun averageLight(pos: BlockPos, entity: BlockEntity): Int {
        var array = intArrayOf(
            getLight(pos.up(), entity),
            getLight(pos.down(), entity),
            getLight(pos.east(), entity),
            getLight(pos.west(), entity),
            getLight(pos.north(), entity),
            getLight(pos.south(), entity)
        )

        return array.average().toInt()
    }

    override fun render( entity: motorBlockEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        var state = Blocks.IRON_BARS.getDefaultState()
        var model = manager.getModel(state)

        if(entity.world!!.isReceivingRedstonePower(entity.pos)) {
            entity.currentRot += (entity.getCachedState().block as motorBlock).currentSpeed
        }

        // the X bar
        matrices.push()

        matrices.translate(0.0, -0.0625*2, 0.0)
        matrices.translate(0.5, 0.5, 0.5)
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90f))
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.currentRot))
        matrices.translate(-0.5, -0.5, -0.5)

        //model.ambientOcclusion = 5

        manager.modelRenderer.render(entity.world, model,  state, entity.pos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)), false, entity.world!!.random, state.getRenderingSeed(entity.pos), OverlayTexture.DEFAULT_UV)

        matrices.pop()

        // the Z bar
        matrices.push()

        matrices.translate(0.0, -0.0625*2, 0.0)
        matrices.translate(0.5, 0.5, 0.5)
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90f))
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.currentRot))
        matrices.translate(-0.5, -0.5, -0.5)

        //model.ambientOcclusion = 5

        manager.modelRenderer.render(entity.world, model,  state, entity.pos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)), false, entity.world!!.random, state.getRenderingSeed(entity.pos), OverlayTexture.DEFAULT_UV)

        matrices.pop()

        // the Y bar
        matrices.push()

        matrices.translate(0.5, 0.5, 0.5)
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-entity.currentRot))
        matrices.translate(-0.5, -0.5, -0.5)

        //model.ambientOcclusion = 5

        manager.modelRenderer.render(entity.world, model,  state, entity.pos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)), false, entity.world!!.random, state.getRenderingSeed(entity.pos), OverlayTexture.DEFAULT_UV)

        matrices.pop()
    }
}