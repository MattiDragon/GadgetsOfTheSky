package io.github.mattidragon.gadgets_of_the_sky;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModStatusEffects {
    public static final StatusEffect AIR_SWIMMING = new StatusEffect(StatusEffectCategory.BENEFICIAL, 0x2975d9) {};

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, GadgetsOfTheSky.id("air_swimming"), AIR_SWIMMING);
    }
}
