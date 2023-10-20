package net.firemuffin303.mobvote2023.utils.forge;

import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.registry.ModItems;
import net.firemuffin303.mobvote2023.common.registry.ModMobEffects;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ModPlatformImpl {
    public static void holdingCrabClaw(Player player) {
        if(!player.getAttribute(ForgeMod.BLOCK_REACH.get()).hasModifier(MobVote2023.CRAB_REACH)){
            player.getAttribute(ForgeMod.BLOCK_REACH.get()).addTransientModifier(MobVote2023.CRAB_REACH);

        }
    }

    public static void stopHoldingCrabClaw(Player player) {
        if(player.getAttribute(ForgeMod.BLOCK_REACH.get()).hasModifier(MobVote2023.CRAB_REACH)){
            player.getAttribute(ForgeMod.BLOCK_REACH.get()).removeModifier(MobVote2023.CRAB_REACH);

        }
    }

    public static <T extends Entity> void registerEntityRenderer(Supplier<EntityType<T>> entityTypeSupplier, EntityRendererProvider<T> entityRendererProvider) {
        EntityRenderers.register(entityTypeSupplier.get(), entityRendererProvider);
    }

    public static <T extends Mob> void registerEntitySpawn(EntityType<T> entityType, SpawnPlacements.Type type, Heightmap.Types heightMapTypes, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType,type,heightMapTypes,predicate);
    }

    public static <T extends Mob> Supplier<Item> registerSpawnEgg(Supplier<EntityType<T>> entityType, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new ForgeSpawnEggItem(entityType,primaryColor,secondaryColor,properties);
    }

    public static <T extends Mob> Supplier<Item> registerMobBucket(Supplier<EntityType<T>> entityType, Supplier<? extends Fluid> fluid, Supplier<? extends SoundEvent> soundEvent, Item.Properties properties) {
        return () -> new MobBucketItem(entityType,fluid,soundEvent,properties);
    }

    public static void registerPotionBrewing(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output) {
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION),input.get())),Ingredient.of(ingredient.get()) ,PotionUtils.setPotion(new ItemStack(Items.POTION),output.get())));
    }

    /*public static void applyLongReach(Player player,int i) {
        if(player.hasEffect(ModMobEffects.CRAB_REACH.get())){
            if(player.getEffect(ModMobEffects.CRAB_REACH.get()).getAmplifier() < i){
                player.getAttribute(ForgeMod.BLOCK_REACH.get()).removeModifier(ModMobEffects.CRAB_LONG_REACH);

            }
        }

        player.getAttribute(ForgeMod.BLOCK_REACH.get()).addTransientModifier(new AttributeModifier(ModMobEffects.CRAB_LONG_REACH,"Crab Effect Reach Modifier",i+1.0d, AttributeModifier.Operation.ADDITION));

    }

    public static void stopApplyLongReach(Player player) {
    }*/

    public static Attribute getReachAttribute() {
        return ForgeMod.BLOCK_REACH.get();
    }
}
