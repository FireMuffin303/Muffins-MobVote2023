package net.firemuffin303.mobvote2023.common.item;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class WolfArmorItem extends Item {
    private static final String TEX_FOLDER = "textures/entity/wolf/";
    private final int protection;
    private final String texture;
    public WolfArmorItem(int protection,String string ,Properties properties) {
        super(properties);
        this.protection = protection;
        this.texture = TEX_FOLDER + "armor/wolf_armor_" + string + ".png";
    }

    public int getProtection() {
        return this.protection;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(MobVote2023.MOD_ID,this.texture);
    }

}
