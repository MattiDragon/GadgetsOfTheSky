package io.github.mattidragon.gadgets_of_the_sky.recipe;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static final RecipeType<SkyAltarRecipe> SKY_ALTAR_TYPE = new RecipeType<>() {};
    public static final RecipeSerializer<SkyAltarRecipe> SKY_ALTAR_SERIALIZER = new SkyAltarRecipe.Serializer();

    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, GadgetsOfTheSky.id("sky_altar"), SKY_ALTAR_TYPE);
        Registry.register(Registries.RECIPE_SERIALIZER, GadgetsOfTheSky.id("sky_altar"), SKY_ALTAR_SERIALIZER);
    }
}
