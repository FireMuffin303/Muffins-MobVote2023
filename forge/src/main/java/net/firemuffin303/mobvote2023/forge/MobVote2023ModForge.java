package net.firemuffin303.mobvote2023.forge;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.entity.CrabEntity;
import net.firemuffin303.mobvote2023.common.registry.ModEntityTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MobVote2023.MOD_ID)
public class MobVote2023ModForge {
    public MobVote2023ModForge() {
        // Submit our event bus to let architectury register our content on the right time
        MobVote2023.init();
        //MobVote2023.postInit();


        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(EventPriority.LOW,this::registerAttribute);
        modEventBus.addListener(EventPriority.LOW,this::registerEntitySpawn);

    }

    public void registerAttribute(EntityAttributeCreationEvent event){
        ModEntityTypes.registerAttributes(((supplier, builderSupplier) -> event.put(supplier.get(),builderSupplier.get().build())));
    }

    public void registerEntitySpawn(SpawnPlacementRegisterEvent spawnPlacements){
        spawnPlacements.register(ModEntityTypes.CRAB.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);

    }


}
