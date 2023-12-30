package io.github.mattidragon.gadgets_of_the_sky.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

class WorldgenProvider extends FabricDynamicRegistryProvider {
    public WorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.TEMPLATE_POOL));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.STRUCTURE));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.STRUCTURE_SET));
    }

    @Override
    public String getName() {
        return "Worldgen";
    }
}
