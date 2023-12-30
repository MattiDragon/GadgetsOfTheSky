package io.github.mattidragon.gadgets_of_the_sky.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import io.github.mattidragon.gadgets_of_the_sky.recipe.SkyAltarRecipe;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SkyAltarEmiRecipe implements EmiRecipe {
    private static final EmiStack FUEL = EmiStack.of(Items.GLOWSTONE_DUST).setChance(1 / 3f);
    private final Identifier id;
    private final EmiIngredient input;
    private final EmiStack output;

    public SkyAltarEmiRecipe(RecipeEntry<SkyAltarRecipe> recipeEntry) {
        id = recipeEntry.id();
        input = EmiIngredient.of(recipeEntry.value().input());
        output = EmiStack.of(recipeEntry.value().output());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return GadgetsOfTheSkyEmiPlugin.SKY_ALTAR_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input, FUEL);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 70;
    }

    @Override
    public int getDisplayHeight() {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(input, 0, 0);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 23, 1);
        widgets.addSlot(output, 52, 0).recipeContext(this);
    }
}
