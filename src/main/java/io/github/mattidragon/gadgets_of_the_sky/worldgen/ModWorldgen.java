package io.github.mattidragon.gadgets_of_the_sky.worldgen;

import com.mojang.datafixers.util.Pair;
import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.Structures;

import java.util.List;
import java.util.Map;

public class ModWorldgen {
    public static final RegistryKey<StructureSet> SKY_ISLAND_STRUCTURE_SET = RegistryKey.of(RegistryKeys.STRUCTURE_SET, GadgetsOfTheSky.id("sky_island"));
    public static final RegistryKey<Structure> SKY_ISLAND_STRUCTURE = RegistryKey.of(RegistryKeys.STRUCTURE, GadgetsOfTheSky.id("sky_island"));
    public static final RegistryKey<StructurePool> SKY_ISLAND_STRUCTURE_POOL = RegistryKey.of(RegistryKeys.TEMPLATE_POOL, GadgetsOfTheSky.id("sky_island"));

    public static void bootstrapStructureSets(Registerable<StructureSet> registerable) {
        var structureLookup = registerable.getRegistryLookup(RegistryKeys.STRUCTURE);

        registerable.register(SKY_ISLAND_STRUCTURE_SET, new StructureSet(
                structureLookup.getOrThrow(SKY_ISLAND_STRUCTURE),
                new RandomSpreadStructurePlacement(16, 8, SpreadType.LINEAR, 2034699100)
        ));
    }

    public static void bootstrapStructures(Registerable<Structure> registerable) {
        var biomeLookup = registerable.getRegistryLookup(RegistryKeys.BIOME);
        var poolLookup = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);

        registerable.register(SKY_ISLAND_STRUCTURE,
                new JigsawStructure(
                        Structures.createConfig(biomeLookup.getOrThrow(ModWorldgenTags.HAS_ISLAND), Map.of(), GenerationStep.Feature.VEGETAL_DECORATION, StructureTerrainAdaptation.NONE),
                        poolLookup.getOrThrow(SKY_ISLAND_STRUCTURE_POOL),
                        1,
                        ConstantHeightProvider.create(YOffset.fixed(50)),
                        false,
                        Heightmap.Type.WORLD_SURFACE_WG
                )
        );
    }

    public static void bootstrapTemplatePools(Registerable<StructurePool> registerable) {
        var poolLookup = registerable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        var emptyPool = poolLookup.getOrThrow(StructurePools.EMPTY);

        registerable.register(SKY_ISLAND_STRUCTURE_POOL, new StructurePool(emptyPool, List.of(
                Pair.of(StructurePoolElement.ofLegacySingle(GadgetsOfTheSky.id("sky_island").toString()), 1)
        ), StructurePool.Projection.RIGID));
    }
}
