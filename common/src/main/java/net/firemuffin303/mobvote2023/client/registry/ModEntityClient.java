package net.firemuffin303.mobvote2023.client.registry;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.client.model.ArmadilloModel;
import net.firemuffin303.mobvote2023.client.model.CrabModel;
import net.firemuffin303.mobvote2023.client.model.PenguinModel;
import net.firemuffin303.mobvote2023.client.renderer.ArmadilloRenderer;
import net.firemuffin303.mobvote2023.client.renderer.CrabRenderer;
import net.firemuffin303.mobvote2023.client.renderer.PenguinRenderer;
import net.firemuffin303.mobvote2023.common.entity.Penguin;
import net.firemuffin303.mobvote2023.common.registry.ModEntityTypes;
import net.firemuffin303.mobvote2023.utils.ModPlatform;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

import java.util.function.Supplier;

public class ModEntityClient {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MobVote2023.MOD_ID, "wolf_armor"), "main");


    public static void init(){
        ModPlatform.registerEntityRenderer(ModEntityTypes.CRAB, CrabRenderer::new);
        ModPlatform.registerEntityRenderer(ModEntityTypes.ARMADILLO, ArmadilloRenderer::new);
        ModPlatform.registerEntityRenderer(ModEntityTypes.PENGUIN, PenguinRenderer::new);
    }

    public static void layerRegistry(LayerDefinitionRegistry registry){
        registry.register(CrabModel.LAYER,CrabModel::createBodyLayer);
        registry.register(ArmadilloModel.LAYER_LOCATION,ArmadilloModel::createBodyLayer);
        registry.register(LAYER_LOCATION, WolfModel::createBodyLayer);
        registry.register(PenguinModel.LAYER_LOCATION, PenguinModel::createBodyLayer);
    }

    public static abstract class LayerDefinitionRegistry {
        public abstract void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }
}
