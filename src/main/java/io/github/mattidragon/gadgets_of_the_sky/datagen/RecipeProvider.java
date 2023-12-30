package io.github.mattidragon.gadgets_of_the_sky.datagen;

import io.github.mattidragon.gadgets_of_the_sky.datagen.builder.SkyAltarRecipeJsonBuilder;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItems;
import io.github.mattidragon.gadgets_of_the_sky.worldgen.ModWorldgen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;

class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        SkyAltarRecipeJsonBuilder.create(Ingredient.ofItems(Items.QUARTZ), RecipeCategory.TOOLS, ModItems.AIR_SHARD)
                .criterion("has_altar", createSkyIslandCriterion())
                .offerTo(exporter);
        SkyAltarRecipeJsonBuilder.create(Ingredient.ofItems(Items.GLOW_INK_SAC), RecipeCategory.TOOLS, ModItems.WATER_SHARD)
                .criterion("has_altar", createSkyIslandCriterion())
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AIR_STAFF)
                .pattern("#")
                .pattern("|")
                .pattern("+")
                .input('#', ModItems.AIR_SHARD)
                .input('|', Items.STICK)
                .input('+', Items.DIAMOND)
                .criterion("has_shard", conditionsFromItem(ModItems.AIR_SHARD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WATER_STAFF)
                .pattern("#")
                .pattern("|")
                .pattern("+")
                .input('#', ModItems.WATER_SHARD)
                .input('|', Items.STICK)
                .input('+', Items.DIAMOND)
                .criterion("has_shard", conditionsFromItem(ModItems.WATER_SHARD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WATER_ORB)
                .pattern(" * ")
                .pattern("+#+")
                .pattern(" * ")
                .input('+', ModItems.WATER_SHARD)
                .input('#', Items.DIAMOND)
                .input('*', Items.NAUTILUS_SHELL)
                .criterion("has_shard", conditionsFromItem(ModItems.WATER_SHARD))
                .offerTo(exporter);
    }

    private static AdvancementCriterion<?> createSkyIslandCriterion() {
        return TickCriterion.Conditions.createLocation(LocationPredicate.Builder.createStructure(ModWorldgen.SKY_ISLAND_STRUCTURE));
    }
}
