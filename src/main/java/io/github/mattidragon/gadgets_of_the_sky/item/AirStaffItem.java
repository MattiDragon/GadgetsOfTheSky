package io.github.mattidragon.gadgets_of_the_sky.item;

import io.github.mattidragon.gadgets_of_the_sky.entity.ModEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AirStaffItem extends Item {
    public AirStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            var entity = new WindChargeEntity(ModEntityTypes.WIND_CHARGE, world);
            entity.setOwner(user);
            entity.setPosition(user.getEyePos());
            entity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 2, 1);
            world.spawnEntity(entity);

            stack.damage(1, user, (user2) -> user2.sendToolBreakStatus(user.getActiveHand()));

            user.getItemCooldownManager().set(this, 30);
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_BREEZE_SHOOT,
                    SoundCategory.PLAYERS,
                    0.5f,
                    0.8f
            );
        }
        return TypedActionResult.consume(stack);
    }
}
