package io.github.mattidragon.gadgets_of_the_sky.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItems;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public record SkyAltarRecipe(String group, Ingredient input, ItemStack output) implements Recipe<SimpleInventory> {
    private static final Codec<SkyAltarRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "").forGetter(SkyAltarRecipe::group),
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("input").forGetter(SkyAltarRecipe::input),
            ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(SkyAltarRecipe::output)
    ).apply(instance, SkyAltarRecipe::new));

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        if (matches(inventory, null)) {
            return output.copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public ItemStack createIcon() {
        return ModItems.SKY_ALTAR.getDefaultStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SKY_ALTAR_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SKY_ALTAR_TYPE;
    }

    public static class Serializer implements RecipeSerializer<SkyAltarRecipe> {
        @Override
        public Codec<SkyAltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public SkyAltarRecipe read(PacketByteBuf buf) {
            var group = buf.readString();
            var input = Ingredient.fromPacket(buf);
            var output = buf.readItemStack();
            return new SkyAltarRecipe(group, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, SkyAltarRecipe recipe) {
            buf.writeString(recipe.group);
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
