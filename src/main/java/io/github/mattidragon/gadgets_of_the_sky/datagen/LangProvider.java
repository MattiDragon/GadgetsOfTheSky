package io.github.mattidragon.gadgets_of_the_sky.datagen;

import io.github.mattidragon.gadgets_of_the_sky.ModStatusEffects;
import io.github.mattidragon.gadgets_of_the_sky.block.ModBlocks;
import io.github.mattidragon.gadgets_of_the_sky.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

class LangProvider extends FabricLanguageProvider {
    protected LangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add(ModItems.WATER_ORB, "Water Orb");
        builder.add(ModItems.WATER_STAFF, "Water Staff");
        builder.add(ModItems.AIR_STAFF, "Air Staff");
        builder.add(ModStatusEffects.AIR_SWIMMING, "Air Swimming");
        builder.add(ModBlocks.SKY_ALTAR, "Sky Altar");
        builder.add(ModItems.AIR_SHARD, "Air Shard");
        builder.add(ModItems.WATER_SHARD, "Water Shard");
        builder.add("itemGroup.gadgets_of_the_sky.items", "Gadgets of the Sky");
        builder.add("filled_map.gadgets_of_the_sky.sky_island", "Sky Island Explorer Map");
        builder.add("emi.category.gadgets_of_the_sky.sky_altar", "Sky Altar Transformation");
    }
}
