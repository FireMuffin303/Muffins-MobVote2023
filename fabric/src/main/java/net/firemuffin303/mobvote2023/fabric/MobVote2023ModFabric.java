package net.firemuffin303.mobvote2023.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.fabricmc.api.ModInitializer;
import net.firemuffin303.mobvote2023.common.entity.Armadillo;
import net.firemuffin303.mobvote2023.common.entity.CrabEntity;
import net.firemuffin303.mobvote2023.common.entity.Penguin;
import net.firemuffin303.mobvote2023.common.registry.ModEntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;

public class MobVote2023ModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MobVote2023.init();
        MobVote2023.postInit();
        FabricDefaultAttributeRegistry.register(ModEntityTypes.CRAB.get(), CrabEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.ARMADILLO.get(), Armadillo.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.PENGUIN.get(), Penguin.createAttributes());




        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP), MobCategory.CREATURE,ModEntityTypes.CRAB.get(),10,3,5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SAVANNA,Biomes.SAVANNA_PLATEAU,Biomes.WINDSWEPT_SAVANNA), MobCategory.CREATURE,ModEntityTypes.ARMADILLO.get(),10,3,5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.STONY_SHORE), MobCategory.CREATURE,ModEntityTypes.PENGUIN.get(),10,4,6);

    }

}
