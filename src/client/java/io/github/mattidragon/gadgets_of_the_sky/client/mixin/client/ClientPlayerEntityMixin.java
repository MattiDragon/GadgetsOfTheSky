package io.github.mattidragon.gadgets_of_the_sky.client.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.gadgets_of_the_sky.item.WaterOrbItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isTouchingWater()Z", ordinal = 2))
    private boolean preventSprintCancelingOutsideWater(boolean original) {
        if (WaterOrbItem.canAirSwim((PlayerEntity) (Object) this)) {
            return true;
        }
        return original;
    }
}
