package io.vortetty.washingMachine

import io.vortetty.washingMachine.block.entity.motorBER
import io.vortetty.washingMachine.block.motorBlock
import io.vortetty.washingMachine.washingMachine.Companion.MOTOR_BLOCK_ENTITY
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher


class washingMachineClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(MOTOR_BLOCK_ENTITY) { x: BlockEntityRenderDispatcher -> motorBER(x) }

        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.CUTOUT,
            *washingMachine.blocks.toTypedArray()
        )
    }
}