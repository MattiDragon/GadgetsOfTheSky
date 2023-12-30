package io.github.mattidragon.gadgets_of_the_sky.item;

import io.github.mattidragon.gadgets_of_the_sky.entity.FlyingWaterEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WaterStaffItem extends Item {
    public WaterStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            if (world.getDimension().ultrawarm()) {
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
            } else {
                var entity = new FlyingWaterEntity(user, world);
                entity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 2, 1);
                world.spawnEntity(entity);
            }
            stack.damage(1, user, (user2) -> user2.sendToolBreakStatus(user.getActiveHand()));

            user.getItemCooldownManager().set(this, 30);
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_GENERIC_SWIM,
                    SoundCategory.PLAYERS,
                    0.5f,
                    0.8f
            );
        }
        return TypedActionResult.consume(stack);
    }

    public static void placeWater(World world, BlockPos pos, Direction clickedSide) {
        if (!world.getBlockState(pos).isReplaceable()) pos = pos.offset(clickedSide);

        var newState = Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 3);
        var state1 = world.getBlockState(pos);
        if (state1.isReplaceable()) {
            world.setBlockState(pos, newState);
        }

        for (var side : Direction.Type.HORIZONTAL) {
            var pos2 = pos.offset(side);
            var state2 = world.getBlockState(pos2);
            if (state2.isReplaceable()) {
                world.setBlockState(pos2, newState);
            }
        }
    }
}
