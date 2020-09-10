package io.vortetty.washingMachine

import io.vortetty.washingMachine.block.entity.motorBlockEntity
import io.vortetty.washingMachine.block.motorBlock
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

class washingMachine : ModInitializer {
    companion object {
        lateinit var MOTOR_BLOCK_ENTITY: BlockEntityType<motorBlockEntity>
        lateinit var blocks: List<Block>
    }

    fun buildMotor(speed: Int, tps: Int, identifier: Identifier, settings: FabricBlockSettings): Block{
        var motor: Block = motorBlock(speed, tps/20, settings) // Divide tps by 20 since it schedules in ticks not tps. should always be 20
        Registry.register(Registry.BLOCK, identifier, motor)
        Registry.register(Registry.ITEM, identifier, BlockItem(motor, Item.Settings().group(ItemGroup.REDSTONE)))
        return motor
    }

    fun buildMotor(speed: Int, tps: Int, identifier: Identifier): Block{
        return buildMotor(speed, tps, identifier, FabricBlockSettings.of(Material.METAL).breakByHand(true).resistance(2f).nonOpaque())
    }

    fun buildMotor(speed: Int, identifier: String): Block{
        return buildMotor(speed, 20, Identifier("washing_machine", identifier))
    }

    override fun onInitialize() {
        blocks = listOf(
            buildMotor(2, "motor_a"),
            buildMotor(5, "motor_b"),
            buildMotor(10, "motor_c"),
            buildMotor(15, "motor_d"),
            buildMotor(20, "motor_e"),
            buildMotor(25, "motor_f"),
            buildMotor(50, "motor_s"),
            buildMotor(100, "motor_ss"),
            buildMotor(250, "motor_sss"),
            buildMotor(500, "motor_ssss"),
            buildMotor(750, "motor_sssss"),
            buildMotor(1000, "motor_ssssss"),
            buildMotor(10000, "motor_x"),
            buildMotor(100000, "motor_y"),
            buildMotor(Int.MAX_VALUE, "motor_z")
        )

        MOTOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "washing_machine:motor_block_entity", BlockEntityType.Builder.create(Supplier { motorBlockEntity() }, *blocks.toTypedArray()).build(null))
    }
}

