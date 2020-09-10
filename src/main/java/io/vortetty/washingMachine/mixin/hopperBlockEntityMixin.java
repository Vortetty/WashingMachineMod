package io.vortetty.washingMachine.mixin;

import com.jamieswhiteshirt.clothesline.common.block.ClotheslineAnchorBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class hopperBlockEntityMixin extends BlockEntity {
    public hopperBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Inject(method = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z", at = @At("HEAD"), cancellable = true)
    void mixin(CallbackInfoReturnable<Boolean> cir){
        if(world.getBlockState(pos.offset(this.getCachedState().get(HopperBlock.FACING))).getBlock() instanceof ClotheslineAnchorBlock ||
                        world.getBlockState(pos.up()).getBlock() instanceof ClotheslineAnchorBlock){
            cir.setReturnValue(false);
        }
    }
}
