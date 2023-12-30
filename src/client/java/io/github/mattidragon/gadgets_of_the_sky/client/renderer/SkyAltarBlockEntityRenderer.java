package io.github.mattidragon.gadgets_of_the_sky.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.mattidragon.gadgets_of_the_sky.block.SkyAltarBlockEntity;
import io.github.mattidragon.gadgets_of_the_sky.recipe.ModRecipes;
import io.github.mattidragon.gadgets_of_the_sky.recipe.SkyAltarRecipe;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.math.RotationAxis;

import java.util.Arrays;

public class SkyAltarBlockEntityRenderer implements BlockEntityRenderer<SkyAltarBlockEntity> {
    // A private copy of BufferBuilderStorage so that we don't interfere with normal rendering
    private static final BufferBuilderStorage BUFFER_BUILDER_STORAGE;
    private final ItemRenderer itemRenderer;

    static {
        int cores = Runtime.getRuntime().availableProcessors();
        int usableCores = MinecraftClient.getInstance().is64Bit() ? cores : Math.min(cores, 4);
        BUFFER_BUILDER_STORAGE = new BufferBuilderStorage(usableCores);
    }

    public SkyAltarBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(SkyAltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var world = entity.getWorld();
        if (world == null) return;

        var time = world.getTime() + tickDelta;

        matrices.push();
        matrices.translate(0.5, 0.75, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time * 2));

        var bobScale = getBobScale(entity);
        matrices.translate(0, Math.sin(time / 10) * 0.1 * bobScale, 0);

        var stack = entity.getDisplayStack();
        if (entity.isCrafting()) {
            var craftingTime = entity.getCraftingTime();
            var progress = ((float) craftingTime) / SkyAltarBlockEntity.MAX_CRAFTING_TIME;
            matrices.translate(0, -0.25 * (1 - progress), 0);
            matrices.scale(progress, progress, progress);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(progress * 360 * -2));
        }

        if (stack.isEmpty()) {
            if (!entity.isActive()) {
                RenderSystem.setShaderColor(1, 1, 1, 0.5f);
                stack = Items.HEART_OF_THE_SEA.getDefaultStack();
            } else if (!entity.hasCharge()) {
                RenderSystem.setShaderColor(1, 1, 1, 0.5f);
                stack = Items.GLOWSTONE_DUST.getDefaultStack();
            } else {
                var inputs = world.getRecipeManager()
                        .listAllOfType(ModRecipes.SKY_ALTAR_TYPE)
                        .stream()
                        .map(RecipeEntry::value)
                        .map(SkyAltarRecipe::input)
                        .map(Ingredient::getMatchingStacks)
                        .flatMap(Arrays::stream)
                        .map(ItemVariant::of) // Switch to ItemVariant to make distinct work and ignore count (ItemStacks don't implement equals)
                        .distinct()
                        .toList();
                var recipeIndex = (int) (time / 60) % inputs.size();
                RenderSystem.setShaderColor(1, 1, 1, 0.5f);
                stack = inputs.get(recipeIndex).toStack();
            }
        }

        // Use custom vertex consumer to draw in immediate mode so that we can use a shader color to make the heart of the sea translucent without custom models
        // Shouldn't affect performance too much as there shouldn't be many of these block entities at the same time. Vanilla also does similar things.
        RenderSystem.enableBlend();
        var immediate = BUFFER_BUILDER_STORAGE.getEntityVertexConsumers();
        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, light, overlay, matrices, immediate, world, entity.getPos().hashCode());
        immediate.draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();

        matrices.pop();
    }

    private static float getBobScale(SkyAltarBlockEntity entity) {
        if (entity.isCrafting()) {
            return ((float) entity.getCraftingTime()) / SkyAltarBlockEntity.MAX_CRAFTING_TIME;
        }
        return 1;
    }
}
