package io.vortetty.washingMachine

import io.vortetty.washingMachine.block.entity.motorBlockEntity
import io.vortetty.washingMachine.block.motorBlock
import io.vortetty.washingMachine.item.uselessItem
import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.loot.JLootTable
import net.devtech.arrp.json.loot.JLootTable.entry
import net.devtech.arrp.json.loot.JLootTable.pool
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.resource.ResourcePack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier


class washingMachine : ModInitializer {
    companion object {
        lateinit var MOTOR_BLOCK_ENTITY: BlockEntityType<motorBlockEntity>
        lateinit var blocks: List<Block>
    }

    fun buildMotor(speed: Int, tps: Int, identifier: Identifier, itemIdentifier: Identifier, settings: FabricBlockSettings): Block{
        var motor: Block = motorBlock(speed, tps / 20, settings) // Divide tps by 20 since it schedules in ticks not tps. should always be 20
        Registry.register(Registry.BLOCK, identifier, motor)
        var item: BlockItem = BlockItem(motor, Item.Settings().group(ItemGroup.REDSTONE))
        Registry.register(Registry.ITEM, identifier, item)
        Registry.register(Registry.ITEM, itemIdentifier, uselessItem(Item.Settings().group(ItemGroup.MISC), true))
        return motor
    }

    fun buildMotor(speed: Int, tps: Int, identifier: Identifier, itemIdentifier: Identifier): Block{
        return buildMotor(
            speed,
            tps,
            identifier,
            itemIdentifier,
            FabricBlockSettings.of(Material.METAL).breakByHand(true).resistance(2f).nonOpaque().drops(Identifier(identifier.namespace, identifier.path))
        )
    }

    fun buildMotor(speed: Int, identifier: String, itemIdentifier: String): Block{
        return buildMotor(speed, 20, Identifier("washing_machine", identifier), Identifier("washing_machine", itemIdentifier))
    }

    override fun onInitialize() {
        blocks = listOf(
            buildMotor(2, "motor_a", "motor_core_a"),
            buildMotor(5, "motor_b", "motor_core_b"),
            buildMotor(10, "motor_c", "motor_core_c"),
            buildMotor(15, "motor_d", "motor_core_d"),
            buildMotor(20, "motor_e", "motor_core_e"),
            buildMotor(25, "motor_f", "motor_core_f"),
            buildMotor(50, "motor_s", "motor_core_s"),
            buildMotor(100, "motor_ss", "motor_core_ss"),
            buildMotor(250, "motor_sss", "motor_core_sss"),
            buildMotor(500, "motor_ssss", "motor_core_ssss"),
            buildMotor(750, "motor_sssss", "motor_core_sssss"),
            buildMotor(1000, "motor_ssssss", "motor_core_ssssss"),
            buildMotor(10000, "motor_x", "motor_core_x"),
            buildMotor(100000, "motor_y", "motor_core_y"),
            buildMotor(Int.MAX_VALUE, "motor_z", "motor_core_z")
        )

        MOTOR_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE, "washing_machine:motor_block_entity", BlockEntityType.Builder.create(
                Supplier { motorBlockEntity() },
                *blocks.toTypedArray()
            ).build(null)
        )

        Registry.register(Registry.ITEM, Identifier("washing_machine:empty_motor_core"), uselessItem(Item.Settings().group(ItemGroup.MISC)))
        Registry.register(Registry.ITEM, Identifier("washing_machine:axle"), uselessItem(Item.Settings().group(ItemGroup.MISC)))
        Registry.register(Registry.ITEM, Identifier("washing_machine:axle_set"), uselessItem(Item.Settings().group(ItemGroup.MISC)))
        Registry.register(Registry.ITEM, Identifier("washing_machine:frame_bar"), uselessItem(Item.Settings().group(ItemGroup.MISC)))
        Registry.register(Registry.ITEM, Identifier("washing_machine:motor_frame"), uselessItem(Item.Settings().group(ItemGroup.MISC)))
    }
}

