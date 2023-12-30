package io.github.mattidragon.gadgets_of_the_sky.client;

import io.github.mattidragon.gadgets_of_the_sky.block.ModBlocks;
import io.github.mattidragon.gadgets_of_the_sky.client.renderer.SkyAltarBlockEntityRenderer;
import io.github.mattidragon.gadgets_of_the_sky.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.WindChargeEntityRenderer;

public class GadgetsOfTheSky implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
//		HudRenderCallback.EVENT.register((context, tickDelta) -> {
//			var client = MinecraftClient.getInstance();
//			context.drawText(client.textRenderer, "Sprinting: " + (client.player == null ? null : client.player.isSprinting()), 10, 10, 0xFFFFFF, true);
//			context.drawText(client.textRenderer, "Swimming: " + (client.player == null ? null : client.player.isSwimming()), 10, 20, 0xFFFFFF, true);
//			context.drawText(client.textRenderer, "Swimming pose: " + (client.player == null ? null : client.player.isInSwimmingPose()), 10, 30, 0xFFFFFF, true);
//		});

		EntityRendererRegistry.register(ModEntityTypes.FLYING_WATER, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.WIND_CHARGE, WindChargeEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlocks.SKY_ALTAR_BLOCK_ENTITY, SkyAltarBlockEntityRenderer::new);
	}
}