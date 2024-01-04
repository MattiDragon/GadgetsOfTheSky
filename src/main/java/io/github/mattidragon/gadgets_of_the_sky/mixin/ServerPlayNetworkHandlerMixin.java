package io.github.mattidragon.gadgets_of_the_sky.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayNetworkHandler.class)
public interface ServerPlayNetworkHandlerMixin {
    @Accessor("floatingTicks")
    void gadgets_of_the_sky$setFloatingTicks(int ticks);
}
