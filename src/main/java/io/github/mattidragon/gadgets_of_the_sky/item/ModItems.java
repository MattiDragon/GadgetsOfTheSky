package io.github.mattidragon.gadgets_of_the_sky.item;

import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import io.github.mattidragon.gadgets_of_the_sky.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final WaterOrbItem WATER_ORB = new WaterOrbItem(new Item.Settings().maxDamage(50));
    public static final WaterStaffItem WATER_STAFF = new WaterStaffItem(new Item.Settings().maxDamage(128));
    public static final AirStaffItem AIR_STAFF = new AirStaffItem(new Item.Settings().maxDamage(128));
    public static final BlockItem SKY_ALTAR = new BlockItem(ModBlocks.SKY_ALTAR, new Item.Settings());
    public static final Item AIR_SHARD = new Item(new Item.Settings());
    public static final Item WATER_SHARD = new Item(new Item.Settings());

    public static void register() {
        Registry.register(Registries.ITEM, GadgetsOfTheSky.id("water_orb"), WATER_ORB);
        Registry.register(Registries.ITEM, GadgetsOfTheSky.id("water_staff"), WATER_STAFF);
        Registry.register(Registries.ITEM, GadgetsOfTheSky.id("air_staff"), AIR_STAFF);
        Registry.register(Registries.ITEM, GadgetsOfTheSky.id("sky_altar"), SKY_ALTAR);
        Registry.register(Registries.ITEM, GadgetsOfTheSky.id("air_shard"), AIR_SHARD);
        Registry.register(Registries.ITEM, GadgetsOfTheSky.id("water_shard"), WATER_SHARD);
    }
}
