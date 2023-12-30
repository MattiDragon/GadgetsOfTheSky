package io.github.mattidragon.gadgets_of_the_sky.client.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.gadgets_of_the_sky.item.WaterOrbItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @ModifyExpressionValue(method = "setupTransforms(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isTouchingWater()Z"))
    private boolean enableVerticalRotationForAirSwimming(boolean original, AbstractClientPlayerEntity player) {
        return WaterOrbItem.isAirSwimming(player) || original;
    }
}
