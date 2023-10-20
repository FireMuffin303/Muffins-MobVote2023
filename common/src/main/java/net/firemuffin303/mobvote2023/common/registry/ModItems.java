package net.firemuffin303.mobvote2023.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.item.ModSmithingTemplateItem;
import net.firemuffin303.mobvote2023.common.item.WolfArmorItem;
import net.firemuffin303.mobvote2023.utils.ModPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Supplier;

public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, MobVote2023.MOD_ID);
    //Crab
    public static final RegistryEntry<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg", ModPlatform.registerSpawnEgg(ModEntityTypes.CRAB,0x5c5dbc,0xf0784f,new Item.Properties()));
    public static final RegistryEntry<Item> CRAB_CLAW = ITEMS.register("crab_claw",() -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> CRAB_BUCKET = ITEMS.register("crab_bucket",ModPlatform.registerMobBucket(ModEntityTypes.CRAB, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH,new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> CRAB_EGG = ITEMS.register("crab_egg",() -> new BlockItem(ModBlocks.CRAB_EGG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRAB_MEAT = ITEMS.register("crab_meat",() -> new Item(new Item.Properties().food(ModFood.CRAB)));
    public static final RegistryEntry<Item> COOKED_CRAB_MEAT = ITEMS.register("cooked_crab_meat",() -> new Item(new Item.Properties().food(ModFood.COOKED_CRAB)));
    public static final RegistryEntry<Item> CRAB_MEAT_WITH_SEAFOOD = ITEMS.register("cooked_seafood_crab_meat",() -> new Item(new Item.Properties().food(ModFood.CRAB_WITH_SEAFOOD)));
    public static final RegistryEntry<Item> SEAFOOD_BUCKET = ITEMS.register("seafood_bucket",() -> new BucketItem(ModFluid.SEAFOOD.get(),new Item.Properties().stacksTo(1)));


    //Armadillo
    public static final RegistryEntry<Item> ARMADILLO_SPAWN_EGG = ITEMS.register("armadillo_spawn_egg", ModPlatform.registerSpawnEgg(ModEntityTypes.ARMADILLO,0xb06d35,0x973a29,new Item.Properties()));
    public static final RegistryEntry<Item> ARMADILLO_SCUTE = ITEMS.register("armadillo_scute",() -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ARMADILLO_DOG_ARMOR = ITEMS.register("armadillo_wolf_armor",() -> new WolfArmorItem(15,"armadillo",new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> AQUA_DOG_ARMOR = ITEMS.register("turtle_armadillo_wolf_armor",() -> new WolfArmorItem(18,"turtle_armadillo",new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> TURTLE_ARMOR_SMITHING_TEMPLATE = ITEMS.register("turtle_upgrade_smithing_template", ModSmithingTemplateItem::createTurtleArmadilloTemplate);

    //Penguin
    public static final RegistryEntry<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", ModPlatform.registerSpawnEgg(ModEntityTypes.PENGUIN,0x423c4d,0xf56e55,new Item.Properties()));
    public static final RegistryEntry<Item> PENGUIN_FEATHER = ITEMS.register("penguin_feather", () -> new Item(new Item.Properties()));



    public static final Supplier<CreativeModeTab> TAB = new ResourcefulCreativeTab(new ResourceLocation(MobVote2023.MOD_ID,"main"))
            .setItemIcon(ModItems.CRAB_CLAW).addRegistry(ITEMS).build();


    static class ModFood{
        public static final FoodProperties CRAB = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).meat().build();
        public static final FoodProperties COOKED_CRAB = new FoodProperties.Builder().nutrition(5).saturationMod(0.3F).meat().build();
        public static final FoodProperties CRAB_WITH_SEAFOOD = new FoodProperties.Builder().nutrition(5).saturationMod(0.3F).alwaysEat().effect(new MobEffectInstance(MobEffects.WATER_BREATHING,200,0),1.0f).meat().build();
    }


}
