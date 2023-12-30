package io.github.mattidragon.gadgets_of_the_sky.client.compat.emi;

import dev.emi.emi.api.render.EmiTexture;
import io.github.mattidragon.gadgets_of_the_sky.GadgetsOfTheSky;
import net.minecraft.util.Identifier;

public class ModEmiTextures {
    public static final Identifier SPRITES = GadgetsOfTheSky.id("textures/gui/emi_sprites.png");
    public static final EmiTexture SKY_ALTAR_SIMPLE = new EmiTexture(SPRITES, 0, 0, 16, 16);
}
