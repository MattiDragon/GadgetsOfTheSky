package io.github.mattidragon.gadgets_of_the_sky.entity;

import io.github.mattidragon.gadgets_of_the_sky.item.WaterStaffItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FlyingWaterEntity extends ThrownEntity {
    private int life = 40;

    public FlyingWaterEntity(EntityType<FlyingWaterEntity> entityType, World world) {
        super(entityType, world);
    }

    public FlyingWaterEntity(LivingEntity owner, World world) {
        super(ModEntityTypes.FLYING_WATER, owner, world);
    }

    @Override
    public void tick() {
        var world = getWorld();
        if (world.isClient()) {
            var random = world.random;
            for (int i = 0; i < life / 4; i++) {
                world.addImportantParticle(ParticleTypes.FALLING_WATER,
                        getX() + (random.nextFloat() - 0.5f) * 0.75,
                        getY() + (random.nextFloat() - 0.5f) * 0.75,
                        getZ() + (random.nextFloat() - 0.5f) * 0.75,
                        0,
                        0,
                        0);
                world.addImportantParticle(ParticleTypes.BUBBLE_POP,
                        getX() + (random.nextFloat() - 0.5f) * 0.75,
                        getY() + (random.nextFloat() - 0.5f) * 0.75,
                        getZ() + (random.nextFloat() - 0.5f) * 0.75,
                        (random.nextFloat() - 0.5f) * 0.1,
                        (random.nextFloat() - 0.25f) * 0.1,
                        (random.nextFloat() - 0.5f) * 0.1);
            }
        }
        if (world.getDimension().ultrawarm()) {
            playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

            }
        life--;
        if (life < 0) {
            this.discard();
        }
        super.tick();
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("life", life);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        life = nbt.getInt("life");
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld() instanceof ServerWorld) {
            if (hitResult instanceof BlockHitResult block) {
                WaterStaffItem.placeWater(getWorld(), block.getBlockPos(), block.getSide());
            } else if (hitResult instanceof EntityHitResult entity) {
                WaterStaffItem.placeWater(getWorld(), entity.getEntity().getBlockPos(), Direction.UP);
            }
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1, 1);
            }

            this.discard();
        }
    }
}
