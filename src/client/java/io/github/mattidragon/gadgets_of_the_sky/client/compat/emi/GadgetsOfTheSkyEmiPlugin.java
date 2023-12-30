package io.github.mattidragon.gadgets_of_the_sky.client.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItems;
import io.github.mattidragon.gadgets_of_the_sky.recipe.ModRecipes;

public class GadgetsOfTheSkyEmiPlugin implements EmiPlugin {
    public static final EmiRecipeCategory SKY_ALTAR_CATEGORY = new EmiRecipeCategory(
            GadgetsOfTheSky.id("sky_altar"),
            EmiStack.of(ModItems.SKY_ALTAR),
            ModEmiTextures.SKY_ALTAR_SIMPLE);


    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(SKY_ALTAR_CATEGORY);
        registry.addWorkstation(SKY_ALTAR_CATEGORY, EmiStack.of(ModItems.SKY_ALTAR));

        for (var recipe : registry.getRecipeManager().listAllOfType(ModRecipes.SKY_ALTAR_TYPE)) {
            registry.addRecipe(new SkyAltarEmiRecipe(recipe));
        }
    }
}
