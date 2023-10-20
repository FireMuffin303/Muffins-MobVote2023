package net.firemuffin303.mobvote2023.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.ModMobEffect;
import net.firemuffin303.mobvote2023.utils.ModPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class ModMobEffects {
    public static final ResourcefulRegistry<MobEffect> MOB_EFFECT = ResourcefulRegistries.create(BuiltInRegistries.MOB_EFFECT, MobVote2023.MOD_ID);

    public static final RegistryEntry<MobEffect> PENGUINS_JET = MOB_EFFECT.register("penguins_jet", () -> new ModMobEffect(MobEffectCategory.BENEFICIAL,0x433d64));
    public static final RegistryEntry<MobEffect> ICE_RESISTANCE = MOB_EFFECT.register("ice_resistance", () -> new ModMobEffect(MobEffectCategory.BENEFICIAL,0x92b9fe));
    public static final RegistryEntry<MobEffect> CRAB_REACH = MOB_EFFECT.register("crab_reach", () -> new ModMobEffect(MobEffectCategory.BENEFICIAL,0xe25f3e).addAttributeModifier(ModPlatform.getReachAttribute(),"6ebf72e4-95f2-4170-b727-f357d7ee1ed9",1.0d, AttributeModifier.Operation.ADDITION));
}
