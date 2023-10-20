package net.firemuffin303.mobvote2023.mixin;

import net.firemuffin303.mobvote2023.client.renderer.layer.WolfArmorLayer;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfRenderer.class)
public abstract class WolfRendererMixin extends MobRenderer<Wolf, WolfModel<Wolf>> {

    public WolfRendererMixin(EntityRendererProvider.Context context, WolfModel<Wolf> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(at =@At("TAIL"),method = "<init>")
    public void init(EntityRendererProvider.Context context, CallbackInfo ci){
        this.addLayer(new WolfArmorLayer(this,context.getModelSet()));
    }

    @Shadow
    @Override
    public ResourceLocation getTextureLocation(Wolf entity) {
        return null;
    }
}
