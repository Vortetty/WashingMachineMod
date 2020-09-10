package io.vortetty.washingMachine.mixin;

import com.jamieswhiteshirt.clothesline.common.impl.NetworkStateImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkStateImpl.class)
public class momentumLimiterMixin {
    @Shadow private int momentum;

    @Inject(method = "Lcom/jamieswhiteshirt/clothesline/common/impl/NetworkStateImpl;setMomentum(I)V", at = @At("TAIL"), remap = false)
    public void setMomentum(int momentum, CallbackInfo ci) {
        this.momentum = momentum;
    }
}
