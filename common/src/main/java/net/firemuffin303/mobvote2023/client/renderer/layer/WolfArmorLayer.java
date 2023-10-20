package net.firemuffin303.mobvote2023.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.firemuffin303.mobvote2023.client.registry.ModEntityClient;
import net.firemuffin303.mobvote2023.common.item.WolfArmorItem;
import net.firemuffin303.mobvote2023.mixin.WolfMixin;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class WolfArmorLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
    private final WolfModel<Wolf> model;


    public WolfArmorLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new WolfModel(entityModelSet.bakeLayer(ModEntityClient.LAYER_LOCATION));

    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Wolf entity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (itemStack.getItem() instanceof WolfArmorItem wolfArmorItem) {
            (this.getParentModel()).copyPropertiesTo(this.model);
            this.model.prepareMobModel(entity, f, g, h);
            this.model.setupAnim(entity, f, g, j, k, l);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(wolfArmorItem.getTexture()));
            this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0F);

        }
    }
}
