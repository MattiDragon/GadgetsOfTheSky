package io.github.mattidragon.gadgets_of_the_sky.item;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroup {
    public static final ItemGroup MOD_ITEM_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.gadgets_of_the_sky.items"))
            .icon(ModItems.SKY_ALTAR::getDefaultStack)
            .entries((context, entries) -> {
                entries.add(ModItems.SKY_ALTAR.getDefaultStack());
                entries.add(ModItems.AIR_SHARD.getDefaultStack());
                entries.add(ModItems.WATER_SHARD.getDefaultStack());
                entries.add(ModItems.AIR_STAFF.getDefaultStack());
                entries.add(ModItems.WATER_STAFF.getDefaultStack());
                entries.add(ModItems.WATER_ORB.getDefaultStack());
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, GadgetsOfTheSky.id("items"), MOD_ITEM_GROUP);
    }
}
