package io.github.mattidragon.gadgets_of_the_sky.item;

import io.github.mattidragon.gadgets_of_the_sky.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WaterOrbItem extends Item {
    private static final int EFFECT_DURATION = 20 * 10;
    private static final int COOLDOWN = 20 * 2;

    public WaterOrbItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, EFFECT_DURATION + COOLDOWN);
        world.playSoundFromEntity(user, SoundEvents.BLOCK_CONDUIT_ACTIVATE, SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.AIR_SWIMMING, EFFECT_DURATION, 0, false, false, true));
        }
        var stack = user.getStackInHand(hand);
        stack.damage(1, user, (entity) -> entity.sendToolBreakStatus(hand));
        return TypedActionResult.success(stack);
    }

    public static boolean canAirSwim(PlayerEntity player) {
        return player.hasStatusEffect(ModStatusEffects.AIR_SWIMMING);
    }

    public static boolean isAirSwimming(PlayerEntity player) {
        return canAirSwim(player) && player.isSwimming();
    }
}