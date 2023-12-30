package io.github.mattidragon.gadgets_of_the_sky.block;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final SkyAltarBlock SKY_ALTAR = new SkyAltarBlock(FabricBlockSettings.create()
            .mapColor(MapColor.STONE_GRAY)
            .instrument(Instrument.BASEDRUM)
            .strength(3.5F));

    public static final BlockEntityType<SkyAltarBlockEntity> SKY_ALTAR_BLOCK_ENTITY = BlockEntityType.Builder.create(SkyAltarBlockEntity::new, SKY_ALTAR).build(null);

    public static void register() {
        Registry.register(Registries.BLOCK, GadgetsOfTheSky.id("sky_altar"), SKY_ALTAR);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, GadgetsOfTheSky.id("sky_altar"), SKY_ALTAR_BLOCK_ENTITY);
    }
}
