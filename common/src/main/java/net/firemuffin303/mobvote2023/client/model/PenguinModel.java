package net.firemuffin303.mobvote2023.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.entity.Penguin;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.definitions.FrogAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class PenguinModel <T extends Penguin> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MobVote2023.MOD_ID, "penguin"), "main");
    private final ModelPart body;
    private final ModelPart head;

    public PenguinModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -15.0F, -5.0F, 12.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24).addBox(-5.0F, -6.0F, -4.0F, 10.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition upper = beak.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -18.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyebrows = head.addOrReplaceChild("eyebrows", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition left = eyebrows.addOrReplaceChild("left", CubeListBuilder.create().texOffs(40, 44).addBox(-2.5F, -3.0F, -0.2F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -19.0F, -4.0F));

        PartDefinition right = eyebrows.addOrReplaceChild("right", CubeListBuilder.create().texOffs(38, 25).addBox(-3.25F, -1.75F, -3.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -20.25F, -1.2F));

        PartDefinition left_foot = body.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(36, 16).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.0F, 4.0F));

        PartDefinition right_foot = body.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(34, 0).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -1.0F, 4.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, 0.0F, -4.0F, 1.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -14.0F, 0.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, 0.0F, -4.0F, 1.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -14.0F, 0.0F));

        PartDefinition egg = body.addOrReplaceChild("egg", CubeListBuilder.create().texOffs(24, 50).addBox(-2.0F, -6.0F, -7.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }

    @Override
    public void setupAnim(T entity, float f, float g, float h, float i, float j) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(entity, i, j, h);
        this.body.getChild("egg").visible =  entity.hasEgg();

        this.head.getChild("eyebrows").visible = !this.young;


        if (entity.isInWaterOrBubble()) {
            this.animateWalk(PenguinAnimation.SWIMMING, f, g, 9.0f,100.0f);
        } else {
            this.animateWalk(PenguinAnimation.WALK, f, g, 9.0f,100.0f);
        }

        float k = 0.0F;
        if (entity.isInWater()) {
            k = -2.25F;
        }

        this.body.xRot = -k * 0.45F;
    }

    private void applyHeadRotation(T entity, float f, float g, float h) {
        f = Mth.clamp(f, -30.0F, 30.0F);
        g = Mth.clamp(g, -25.0F, 45.0F);
        this.head.yRot = f * 0.017453292F;
        this.head.xRot = g * 0.017453292F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(0.45F, 0.45F, 0.45F);
            poseStack.translate(0.0F, 1.834375F, 0.0F);
            this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
            poseStack.popPose();
        } else {
            this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
        }
    }

    public static class PenguinAnimation {


        public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1f).looping()
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, -5f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_foot",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.625f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_foot",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_wing",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -20f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -20f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -20f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_wing",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 20f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 20f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 20f),
                                        AnimationChannel.Interpolations.LINEAR))).build();

        public static final AnimationDefinition SWIMMING = AnimationDefinition.Builder.withLength(1f).looping()
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_foot",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(55f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(55f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_foot",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(55f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_wing",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-42f, -43.7f, -37.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-17.55f, -15.78f, -5.37f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-42f, -43.7f, -37.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_wing",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-42f, 43.7f, 37.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-17.55f, 15.78f, 5.37f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-42f, 43.7f, 37.5f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
    }
}
