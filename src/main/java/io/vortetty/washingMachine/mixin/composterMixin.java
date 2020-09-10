package io.vortetty.washingMachine.mixin;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.ComposterBlock.registerCompostableItem;

@Mixin(ComposterBlock.class)
public class composterMixin {
    @Inject(method = "Lnet/minecraft/block/ComposterBlock;registerDefaultCompostableItems()V", at = @At("HEAD"))
    private static void mixin(CallbackInfo ci){
        registerCompostableItem(1F, Items.BONE_MEAL);
    }
}
