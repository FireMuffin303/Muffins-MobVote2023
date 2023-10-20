package net.firemuffin303.mobvote2023.forge;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.client.model.ArmadilloModel;
import net.firemuffin303.mobvote2023.client.model.CrabModel;
import net.firemuffin303.mobvote2023.client.model.PenguinModel;
import net.firemuffin303.mobvote2023.client.registry.ModEntityClient;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MobVote2023.MOD_ID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobVote2023ModForgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(ModEntityClient::init);
    }

    @SubscribeEvent
    public static void registryLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(CrabModel.LAYER,CrabModel::createBodyLayer);
        event.registerLayerDefinition(ArmadilloModel.LAYER_LOCATION,ArmadilloModel::createBodyLayer);
        event.registerLayerDefinition(ModEntityClient.LAYER_LOCATION, WolfModel::createBodyLayer);
        event.registerLayerDefinition(PenguinModel.LAYER_LOCATION, PenguinModel::createBodyLayer);


    }

}
