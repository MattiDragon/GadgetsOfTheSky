package io.github.mattidragon.gadgets_of_the_sky.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.gadgets_of_the_sky.item.WaterOrbItem;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isEmpty()Z"))
    private boolean allowSwimmingVerticallyInAir(boolean original) {
        if (WaterOrbItem.canAirSwim((PlayerEntity) (Object) this))
            return false;
        return original;
    }
}
