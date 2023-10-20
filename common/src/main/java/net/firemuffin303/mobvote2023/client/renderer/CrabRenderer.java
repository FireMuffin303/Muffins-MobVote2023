package net.firemuffin303.mobvote2023.client.renderer;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.client.model.CrabModel;
import net.firemuffin303.mobvote2023.common.entity.CrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel<CrabEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MobVote2023.MOD_ID,"textures/entity/crab/crab.png");


    public CrabRenderer(EntityRendererProvider.Context context) {
        super(context, new CrabModel<>(context.bakeLayer(CrabModel.LAYER)), 0.4F);

    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity entity) {
        return TEXTURE;
    }
}
