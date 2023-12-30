package io.github.mattidragon.gadgets_of_the_sky.datagen;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import io.github.mattidragon.gadgets_of_the_sky.block.ModBlocks;
import io.github.mattidragon.gadgets_of_the_sky.block.SkyAltarBlock;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;

import java.util.Optional;

class ModelProvider extends FabricModelProvider {
    private static final TextureKey PLATE_TEXTURE_KEY = TextureKey.of("plate");

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generateSkyAltar(generator);
    }

    private static void generateSkyAltar(BlockStateModelGenerator generator) {
        var factory = TexturedModel.makeFactory(
                block -> TextureMap.of(PLATE_TEXTURE_KEY, TextureMap.getSubId(block, "_plate_empty")),
                new Model(Optional.of(GadgetsOfTheSky.id("block/sky_altar_template")), Optional.empty(), PLATE_TEXTURE_KEY));

        var emptyModel = factory.upload(ModBlocks.SKY_ALTAR, "_empty", generator.modelCollector);
        var lowModel = factory.get(ModBlocks.SKY_ALTAR)
                .textures(textures -> textures.put(PLATE_TEXTURE_KEY, TextureMap.getSubId(ModBlocks.SKY_ALTAR, "_plate_low")))
                .upload(ModBlocks.SKY_ALTAR, "_low", generator.modelCollector);
        var highModel = factory.get(ModBlocks.SKY_ALTAR)
                .textures(textures -> textures.put(PLATE_TEXTURE_KEY, TextureMap.getSubId(ModBlocks.SKY_ALTAR, "_plate_high")))
                .upload(ModBlocks.SKY_ALTAR, "_high", generator.modelCollector);
        var fullModel = factory.get(ModBlocks.SKY_ALTAR)
                .textures(textures -> textures.put(PLATE_TEXTURE_KEY, TextureMap.getSubId(ModBlocks.SKY_ALTAR, "_plate_full")))
                .upload(ModBlocks.SKY_ALTAR, "_full", generator.modelCollector);

        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.SKY_ALTAR)
                .coordinate(BlockStateVariantMap.create(SkyAltarBlock.CHARGES)
                        .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, emptyModel))
                        .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, lowModel))
                        .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, highModel))
                        .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, fullModel))));
        generator.excludeFromSimpleItemModelGeneration(ModBlocks.SKY_ALTAR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.WATER_ORB, Models.GENERATED);
        generator.register(ModItems.WATER_STAFF, Models.HANDHELD);
        generator.register(ModItems.AIR_STAFF, Models.HANDHELD);
        generator.register(ModItems.AIR_SHARD, Models.GENERATED);
        generator.register(ModItems.WATER_SHARD, Models.GENERATED);
        generator.register(ModItems.SKY_ALTAR, new Model(Optional.of(GadgetsOfTheSky.id("block/sky_altar_empty")), Optional.empty()));
    }
}
