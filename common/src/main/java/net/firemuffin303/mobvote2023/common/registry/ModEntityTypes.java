package net.firemuffin303.mobvote2023.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.entity.Armadillo;
import net.firemuffin303.mobvote2023.common.entity.CrabEntity;
import net.firemuffin303.mobvote2023.common.entity.Penguin;
import net.firemuffin303.mobvote2023.mixin.SpawnPlacementMixin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPE = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, MobVote2023.MOD_ID);

    public static final RegistryEntry<EntityType<CrabEntity>> CRAB = ENTITY_TYPE.register("crab",() -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE).sized(0.8f,0.5f).build(MobVote2023.MOD_ID));
    public static final RegistryEntry<EntityType<Armadillo>> ARMADILLO = ENTITY_TYPE.register("armadillo",() -> EntityType.Builder.of(Armadillo::new, MobCategory.CREATURE).sized(0.8f,0.8f).build(MobVote2023.MOD_ID));
    public static final RegistryEntry<EntityType<Penguin>> PENGUIN = ENTITY_TYPE.register("penguin",() -> EntityType.Builder.of(Penguin::new, MobCategory.CREATURE).sized(0.8f,1.2f).build(MobVote2023.MOD_ID));


    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes){
        attributes.accept(CRAB,CrabEntity::createAttributes);
        attributes.accept(ARMADILLO,Armadillo::createAttributes);
        attributes.accept(PENGUIN,Penguin::createAttributes);


    }

    public static void postInit(){
        SpawnPlacementMixin.invokeRegister(ModEntityTypes.CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::checkSpawnRules);
        SpawnPlacementMixin.invokeRegister(ModEntityTypes.ARMADILLO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacementMixin.invokeRegister(ModEntityTypes.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }


}
