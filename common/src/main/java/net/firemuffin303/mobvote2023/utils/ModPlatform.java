package net.firemuffin303.mobvote2023.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.firemuffin303.mobvote2023.mixin.PlayerMixin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class ModPlatform {
    @ExpectPlatform
    public static void holdingCrabClaw(Player player){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void stopHoldingCrabClaw(Player player){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererProvider<T> entityRendererProvider){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<Item> registerSpawnEgg(Supplier<EntityType<T>> entityType, int primaryColor, int secondaryColor, Item.Properties properties){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<Item> registerMobBucket(Supplier<EntityType<T>> entityType, Supplier<? extends Fluid> fluid, Supplier<? extends SoundEvent> soundEvent, Item.Properties properties){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> void registerEntitySpawn(EntityType<T> entityType, SpawnPlacements.Type type, Heightmap.Types heightMapTypes, SpawnPlacements.SpawnPredicate<T> predicate) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPotionBrewing(Supplier<Potion> input,Supplier<Item> ingredient,Supplier<Potion> output) {
        throw new AssertionError();
    }

    /*@ExpectPlatform
    public static void applyLongReach(Player player,int i){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void stopApplyLongReach(Player player) {
        throw new AssertionError();
    }*/

    @ExpectPlatform
    public static Attribute getReachAttribute() {
        throw new AssertionError();
    }
}
