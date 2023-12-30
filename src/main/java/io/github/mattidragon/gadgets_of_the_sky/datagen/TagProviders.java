package io.github.mattidragon.gadgets_of_the_sky.datagen;

import io.github.mattidragon.gadgets_of_the_sky.block.ModBlocks;
import io.github.mattidragon.gadgets_of_the_sky.worldgen.ModWorldgen;
import io.github.mattidragon.gadgets_of_the_sky.worldgen.ModWorldgenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.structure.Structure;

import java.util.concurrent.CompletableFuture;

class TagProviders {
    static class Biomes extends FabricTagProvider<Biome> {
        public Biomes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.BIOME, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ModWorldgenTags.HAS_ISLAND)
                    .add(BiomeKeys.FROZEN_PEAKS)
                    .add(BiomeKeys.JAGGED_PEAKS)
                    .add(BiomeKeys.STONY_PEAKS)
                    .add(BiomeKeys.GROVE)
                    .add(BiomeKeys.SNOWY_SLOPES)
                    .add(BiomeKeys.MEADOW);
        }
    }

    static class Blocks extends FabricTagProvider.BlockTagProvider {
        public Blocks(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(ModBlocks.SKY_ALTAR);
        }
    }

    static class Structures extends FabricTagProvider<Structure> {
        public Structures(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.STRUCTURE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ModWorldgenTags.ON_ISLAND_MAPS)
                    .add(ModWorldgen.SKY_ISLAND_STRUCTURE);
        }
    }
}
