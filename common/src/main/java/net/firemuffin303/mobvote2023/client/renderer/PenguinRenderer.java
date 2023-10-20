package net.firemuffin303.mobvote2023.client.renderer;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.client.model.PenguinModel;
import net.firemuffin303.mobvote2023.common.entity.Penguin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PenguinRenderer extends MobRenderer<Penguin, PenguinModel<Penguin>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MobVote2023.MOD_ID,"textures/entity/penguin/penguin.png");

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel<>(context.bakeLayer(PenguinModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(Penguin entity) {
        return TEXTURE;
    }
}
