package io.github.mattidragon.gadgets_of_the_sky.datagen;

import io.github.mattidragon.gadgets_of_the_sky.worldgen.ModWorldgen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class GadgetsOfTheSkyDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(LangProvider::new);
		pack.addProvider(WorldgenProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(TagProviders.Biomes::new);
		pack.addProvider(TagProviders.Blocks::new);
		pack.addProvider(TagProviders.Structures::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder builder) {
		builder.addRegistry(RegistryKeys.TEMPLATE_POOL, ModWorldgen::bootstrapTemplatePools);
		builder.addRegistry(RegistryKeys.STRUCTURE, ModWorldgen::bootstrapStructures);
		builder.addRegistry(RegistryKeys.STRUCTURE_SET, ModWorldgen::bootstrapStructureSets);
	}
}
