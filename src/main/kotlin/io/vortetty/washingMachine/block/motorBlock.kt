package io.vortetty.washingMachine.block

import com.jamieswhiteshirt.clothesline.api.NetworkManagerProvider
import com.jamieswhiteshirt.clothesline.api.NetworkNode
import io.vortetty.washingMachine.block.entity.motorBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.GameRules
import net.minecraft.world.World
import java.util.*


class motorBlock(speed: Int, tickrate: Int, settings: FabricBlockSettings): Block(settings), BlockEntityProvider {
    var speed: Int = speed
    var currentSpeed: Int = 0
    var tickrate: Int = tickrate

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val nodeUP = (world as NetworkManagerProvider).networkManager.networks.nodes.get(pos.up())
        val nodeDOWN = (world as NetworkManagerProvider).networkManager.networks.nodes.get(pos.down())
        val nodeN = (world as NetworkManagerProvider).networkManager.networks.nodes.get(pos.north())
        val nodeE = (world as NetworkManagerProvider).networkManager.networks.nodes.get(pos.east())
        val nodeS = (world as NetworkManagerProvider).networkManager.networks.nodes.get(pos.south())
        val nodeW = (world as NetworkManagerProvider).networkManager.networks.nodes.get(pos.west())

        val states = listOf(nodeUP, nodeDOWN, nodeN, nodeS, nodeE, nodeW).toSet().toMutableList()
        states.removeIf { obj: NetworkNode? -> obj == null }

        if(world.isReceivingRedstonePower(pos)) {
            for (i in states) {
                var momentum = i.network.state.momentum
                if(momentum < speed) {
                    i.network.state.momentum = speed
                }
            }
            if(currentSpeed < speed) {
                currentSpeed = speed
            }
        }
        else {
            for (i in states) {
                var momentum = i.network.state.momentum
                if(momentum > 0) {
                    i.network.state.momentum = momentum / 2
                    currentSpeed = i.network.state.momentum
                }
            }
            if(currentSpeed > 0) {
                currentSpeed /= 2
            }
        }
        world.getBlockTickScheduler().schedule(pos, this, tickrate)
    }

    override fun neighborUpdate(
        state: BlockState,
        world: World,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        notify: Boolean
    ) {
        world.getBlockTickScheduler().schedule(pos, this, tickrate)
    }

    override fun createBlockEntity(world: BlockView?): BlockEntity? {
        var entity = motorBlockEntity()
        entity.speed = speed
        return entity
    }

    override fun getRenderType(state: BlockState?): BlockRenderType? {
        //return super.getRenderType(state)
        return BlockRenderType.MODEL
    }
}