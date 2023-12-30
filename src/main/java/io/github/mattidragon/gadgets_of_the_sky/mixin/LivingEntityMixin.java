package io.github.mattidragon.gadgets_of_the_sky.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import io.github.mattidragon.gadgets_of_the_sky.item.WaterOrbItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true)
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isTouchingWater()Z", ordinal = 0))
    private boolean applySwimPhysicsInAir(boolean original) {
        //noinspection ConstantValue
        return original || ((Object) this instanceof PlayerEntity player && WaterOrbItem.isAirSwimming(player));
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isTouchingWater()Z", ordinal = 0))
    private boolean allowJumpFloatingInAir(boolean original, @Local(ordinal = 3) LocalDoubleRef fluidHeight) {
        fluidHeight.set(1);
        //noinspection ConstantValue
        return original || ((Object) this instanceof PlayerEntity player && WaterOrbItem.isAirSwimming(player));
    }

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;doesNotCollide(DDD)Z", ordinal = 0))
    private boolean fixWierdClimbing(boolean original) {
        //noinspection ConstantValue
        if ((Object) this instanceof PlayerEntity player && WaterOrbItem.canAirSwim(player) && !player.isTouchingWater()) {
            return false;
        }
        return original;
    }

    @ModifyExpressionValue(method = "tickMovement", slice = @Slice(from = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/entity/effect/StatusEffects;LEVITATION:Lnet/minecraft/entity/effect/StatusEffect;")), at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean disableFallDamage(boolean original) {
        //noinspection ConstantValue
        if ((Object) this instanceof PlayerEntity player && WaterOrbItem.canAirSwim(player) && !player.isTouchingWater()) {
            return true;
        }
        return original;
    }
}
