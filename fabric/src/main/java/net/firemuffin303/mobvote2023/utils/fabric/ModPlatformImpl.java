package net.firemuffin303.mobvote2023.utils.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.ModMobEffect;
import net.firemuffin303.mobvote2023.common.registry.ModMobEffects;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class ModPlatformImpl {

    public static void holdingCrabClaw(Player player) {
        if(!player.getAttribute(ReachEntityAttributes.REACH).hasModifier(MobVote2023.CRAB_REACH)){
            player.getAttribute(ReachEntityAttributes.REACH).addTransientModifier(MobVote2023.CRAB_REACH);
        }
    }

    public static void stopHoldingCrabClaw(Player player) {
        if(player.getAttribute(ReachEntityAttributes.REACH).hasModifier(MobVote2023.CRAB_REACH)){
            player.getAttribute(ReachEntityAttributes.REACH).removeModifier(MobVote2023.CRAB_REACH);
        }
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererProvider<T> entityRendererProvider) {
        EntityRendererRegistry.register(entityTypeSupplier.get(),entityRendererProvider);

    }

    public static <T extends Mob> void registerEntitySpawn(EntityType<T> entityType, SpawnPlacements.Type type, Heightmap.Types heightMapTypes, SpawnPlacements.SpawnPredicate<T> predicate) {
        //SpawnPlacementMixin.invokeRegister(entityType,type,heightMapTypes,predicate);
    }

    public static  <T extends Mob> Supplier<Item> registerSpawnEgg(Supplier<EntityType<T>> entityType, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new SpawnEggItem(entityType.get(),primaryColor,secondaryColor,properties);
    }

    public static <T extends Mob> Supplier<Item> registerMobBucket(Supplier<EntityType<T>> entityType, Supplier<? extends Fluid> fluid, Supplier<? extends SoundEvent> soundEvent, Item.Properties properties) {
        return () -> new MobBucketItem(entityType.get(),fluid.get(),soundEvent.get(),properties);
    }

    public static void registerPotionBrewing(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output) {
        FabricBrewingRecipeRegistry.registerPotionRecipe(input.get(), Ingredient.of(ingredient.get()),output.get());
    }

    /*public static void applyLongReach(Player player,int i) {
        if(player.hasEffect(ModMobEffects.CRAB_REACH.get())){
            if(player.getEffect(ModMobEffects.CRAB_REACH.get()).getAmplifier() < i){
                if(player.getAttribute(ReachEntityAttributes.REACH).getModifier(ModMobEffects.CRAB_LONG_REACH) != null) {
                    player.getAttribute(ReachEntityAttributes.REACH).removeModifier(ModMobEffects.CRAB_LONG_REACH);
                }
                player.getAttribute(ReachEntityAttributes.REACH).addTransientModifier(new AttributeModifier(ModMobEffects.CRAB_LONG_REACH,"Range Modifier",i+1.0d, AttributeModifier.Operation.ADDITION));


            }
        }else{
            if(player.getAttribute(ReachEntityAttributes.REACH).getModifier(ModMobEffects.CRAB_LONG_REACH) != null){
                player.getAttribute(ReachEntityAttributes.REACH).removeModifier(ModMobEffects.CRAB_LONG_REACH);

            }
            MobVote2023.LOGGER.debug("apply");
            player.getAttribute(ReachEntityAttributes.REACH).addTransientModifier(new AttributeModifier(ModMobEffects.CRAB_LONG_REACH,"Range Modifier",i+1.0d, AttributeModifier.Operation.ADDITION));

        }
    }

    public static void stopApplyLongReach(Player player) {
        if(player.getAttribute(ReachEntityAttributes.REACH).getModifier(ModMobEffects.CRAB_LONG_REACH) != null){
            player.getAttribute(ReachEntityAttributes.REACH).removeModifier(ModMobEffects.CRAB_LONG_REACH);
        }
    }*/

    public static Attribute getReachAttribute() {
        return ReachEntityAttributes.REACH;
    }
}
