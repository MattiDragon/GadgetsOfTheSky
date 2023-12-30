package io.github.mattidragon.gadgets_of_the_sky;

import io.github.mattidragon.gadgets_of_the_sky.block.ModBlocks;
import io.github.mattidragon.gadgets_of_the_sky.entity.ModEntityTypes;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItemGroup;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItems;
import io.github.mattidragon.gadgets_of_the_sky.recipe.ModRecipes;
import io.github.mattidragon.gadgets_of_the_sky.worldgen.ModWorldgenTags;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ExplorationMapLootFunction;
import net.minecraft.loot.function.SetNameLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GadgetsOfTheSky implements ModInitializer {
    public static final String MOD_ID = "gadgets_of_the_sky";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
	public void onInitialize() {
        ModItems.register();
        ModBlocks.register();
        ModStatusEffects.register();
        ModEntityTypes.register();
        ModRecipes.register();
        ModItemGroup.register();

        injectLootTable();
	}

    private static void injectLootTable() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(new Identifier("minecraft:chests/buried_treasure"))) {
                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(
                                ItemEntry.builder(Items.MAP)
                                        .apply(
                                                ExplorationMapLootFunction.builder()
                                                        .withDestination(ModWorldgenTags.ON_ISLAND_MAPS)
                                                        .withDecoration(MapIcon.Type.TARGET_POINT)
                                                        .withZoom((byte)1)
                                                        .withSkipExistingChunks(false)
                                        )
                                        .apply(SetNameLootFunction.builder(Text.translatable("filled_map.gadgets_of_the_sky.sky_island")))
                        ));
            }
        });
    }
}