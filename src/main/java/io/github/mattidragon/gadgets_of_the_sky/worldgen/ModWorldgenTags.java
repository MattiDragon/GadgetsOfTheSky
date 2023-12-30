package io.github.mattidragon.gadgets_of_the_sky.worldgen;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

public class ModWorldgenTags {
    public static final TagKey<Biome> HAS_ISLAND = TagKey.of(RegistryKeys.BIOME, GadgetsOfTheSky.id("has_structure/sky_island"));
    public static final TagKey<Structure> ON_ISLAND_MAPS = TagKey.of(RegistryKeys.STRUCTURE, GadgetsOfTheSky.id("on_island_maps"));
}
