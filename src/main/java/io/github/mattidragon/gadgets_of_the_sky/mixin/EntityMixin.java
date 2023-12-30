package io.github.mattidragon.gadgets_of_the_sky.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.gadgets_of_the_sky.item.WaterOrbItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract boolean isTouchingWater();

    @Shadow public abstract boolean isOnGround();

    @ModifyExpressionValue(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"))
    private boolean makeSwimmingContinueInAir(boolean original) {
        //noinspection ConstantValue
        return ((Object) this instanceof PlayerEntity player && WaterOrbItem.canAirSwim(player)) || original;
    }

    @ModifyExpressionValue(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSubmergedInWater()Z"))
    private boolean makeSwimmingStartInAir1(boolean original) {
        //noinspection ConstantValue
        return ((Object) this instanceof PlayerEntity player && WaterOrbItem.canAirSwim(player) && (isTouchingWater() || !isOnGround())) || original;
    }

    @ModifyExpressionValue(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean makeSwimmingStartInAir2(boolean original) {
        //noinspection ConstantValue
        return ((Object) this instanceof PlayerEntity player && WaterOrbItem.canAirSwim(player)) || original;
    }

    @ModifyExpressionValue(method = "isCrawling", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"))
    private boolean makeAirSwimmingNotCrawling(boolean original) {
        //noinspection ConstantValue
        return ((Object) this instanceof PlayerEntity player && WaterOrbItem.isAirSwimming(player)) || original;
    }
}
