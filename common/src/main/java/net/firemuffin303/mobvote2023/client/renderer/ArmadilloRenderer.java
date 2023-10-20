package net.firemuffin303.mobvote2023.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.client.model.ArmadilloModel;
import net.firemuffin303.mobvote2023.client.model.CrabModel;
import net.firemuffin303.mobvote2023.common.entity.Armadillo;
import net.firemuffin303.mobvote2023.common.entity.CrabEntity;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ArmadilloRenderer extends MobRenderer<Armadillo, ArmadilloModel<Armadillo>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MobVote2023.MOD_ID,"textures/entity/armadillo/armadillo.png");
    private static final ResourceLocation TEXTURE_DEFENSE = new ResourceLocation(MobVote2023.MOD_ID,"textures/entity/armadillo/armadillo_defense.png");

    public ArmadilloRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmadilloModel<>(context.bakeLayer(ArmadilloModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(Armadillo entity) {
        return entity.isDefenseMode() ? TEXTURE_DEFENSE : TEXTURE;
    }

    @Override
    public void render(Armadillo mob, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(mob, f, g, poseStack, multiBufferSource, i);
    }
}
