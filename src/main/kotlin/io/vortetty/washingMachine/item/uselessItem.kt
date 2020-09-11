package io.vortetty.washingMachine.item

import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class uselessItem : Item {
    var glint: Boolean

    constructor(settings: Settings?) : super(settings) {
        glint = false
    }

    constructor(settings: Settings?, glint: Boolean) : super(settings) {
        this.glint = glint
    }

    override fun damage(source: DamageSource): Boolean {
        return super.damage(source)
    }

    override fun usageTick(world: World, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {}
    override fun postProcessTag(tag: CompoundTag): Boolean {
        return false
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        return true
    }

    override fun asItem(): Item {
        return this
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        return ActionResult.PASS
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        return 1.0f
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        return if (this.isFood) {
            val itemStack = user.getStackInHand(hand)
            if (user.canConsume(foodComponent!!.isAlwaysEdible)) {
                user.setCurrentHand(hand)
                TypedActionResult.consume(itemStack)
            } else {
                TypedActionResult.fail(itemStack)
            }
        } else {
            TypedActionResult.pass(user.getStackInHand(hand))
        }
    }

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        return if (this.isFood) user.eatFood(world, stack) else stack
    }

    override fun isDamageable(): Boolean {
        return false
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        return false
    }

    override fun postMine(stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity): Boolean {
        return false
    }

    override fun isEffectiveOn(state: BlockState): Boolean {
        return false
    }

    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        return ActionResult.success(false)
    }

    override fun getUseAction(stack: ItemStack): UseAction {
        return UseAction.NONE
    }

    override fun hasGlint(stack: ItemStack): Boolean {
        return glint
    }

    override fun isEnchantable(stack: ItemStack): Boolean {
        return false
    }
}