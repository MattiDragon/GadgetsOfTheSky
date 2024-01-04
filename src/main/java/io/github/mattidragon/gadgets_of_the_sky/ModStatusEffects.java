package io.github.mattidragon.gadgets_of_the_sky;

import io.github.mattidragon.gadgets_of_the_sky.mixin.ServerPlayNetworkHandlerMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModStatusEffects {
    public static final StatusEffect AIR_SWIMMING = new StatusEffect(StatusEffectCategory.BENEFICIAL, 0x2975d9) {
        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            return true;
        }

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            if (entity instanceof ServerPlayerEntity player) {
                ((ServerPlayNetworkHandlerMixin) player.networkHandler).gadgets_of_the_sky$setFloatingTicks(0);
            }
        }
    };

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, GadgetsOfTheSky.id("air_swimming"), AIR_SWIMMING);
    }
}
