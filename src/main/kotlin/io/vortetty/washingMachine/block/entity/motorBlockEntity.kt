package io.vortetty.washingMachine.block.entity;

import io.vortetty.washingMachine.washingMachine.Companion.MOTOR_BLOCK_ENTITY
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag




class motorBlockEntity(): BlockEntity(MOTOR_BLOCK_ENTITY) {
    var speed = 0
    var currentRot: Float = 0f

    override fun toTag(tag: CompoundTag): CompoundTag? {
        super.toTag(tag)

        // Save the current value of the number to the tag
        tag.putInt("speed", speed)
        return tag
    }

    override fun fromTag(state: BlockState, tag: CompoundTag) {
        super.fromTag(state, tag)
        speed = tag.getInt("number")
    }
}
