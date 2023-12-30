package io.github.mattidragon.gadgets_of_the_sky.entity;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntityTypes {
    public static final EntityType<FlyingWaterEntity> FLYING_WATER = FabricEntityTypeBuilder
            .<FlyingWaterEntity>create(SpawnGroup.MISC, FlyingWaterEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .trackRangeChunks(4)
            .trackedUpdateRate(10)
            .build();
    public static final EntityType<WindChargeEntity> WIND_CHARGE =
            FabricEntityTypeBuilder.<WindChargeEntity>create(SpawnGroup.MISC, WindChargeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.3125F, 0.3125F))
                    .trackRangeChunks(4)
                    .trackedUpdateRate(10)
                    .build();

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, GadgetsOfTheSky.id("flying_water"), FLYING_WATER);
        Registry.register(Registries.ENTITY_TYPE, GadgetsOfTheSky.id("wind_charge"), WIND_CHARGE);
    }
}
