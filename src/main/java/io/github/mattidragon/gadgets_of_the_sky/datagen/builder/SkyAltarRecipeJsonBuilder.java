package io.github.mattidragon.gadgets_of_the_sky.datagen.builder;

import io.github.mattidragon.gadgets_of_the_sky.recipe.SkyAltarRecipe;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SkyAltarRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
	private final RecipeCategory category;
	private final Item output;
	private final Ingredient input;
	private final int count;
	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	private SkyAltarRecipeJsonBuilder(RecipeCategory category, Ingredient input, ItemConvertible output, int count) {
		this.category = category;
		this.output = output.asItem();
		this.input = input;
		this.count = count;
	}

	public static SkyAltarRecipeJsonBuilder create(Ingredient input, RecipeCategory category, ItemConvertible output) {
		return new SkyAltarRecipeJsonBuilder(category, input, output, 1);
	}

	public SkyAltarRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
		this.criteria.put(string, advancementCriterion);
		return this;
	}

	public SkyAltarRecipeJsonBuilder group(@Nullable String string) {
		this.group = string;
		return this;
	}

	@Override
	public Item getOutputItem() {
		return this.output;
	}

	@Override
	public void offerTo(RecipeExporter exporter, Identifier recipeId) {
		this.validate(recipeId);
        var builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
			.rewards(AdvancementRewards.Builder.recipe(recipeId))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
		this.criteria.forEach(builder::criterion);
		var recipe = new SkyAltarRecipe(Objects.requireNonNullElse(this.group, ""), this.input, new ItemStack(this.output, this.count));
		exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
	}

	private void validate(Identifier recipeId) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + recipeId);
		}
	}
}
