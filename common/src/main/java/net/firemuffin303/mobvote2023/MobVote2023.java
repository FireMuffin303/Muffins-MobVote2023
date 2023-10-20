package net.firemuffin303.mobvote2023;

import net.firemuffin303.mobvote2023.common.block.ModCauldronInteraction;
import net.firemuffin303.mobvote2023.common.registry.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MobVote2023 {
    public static final Logger LOGGER = LoggerFactory.getLogger(MobVote2023.MOD_ID);
    public static final String MOD_ID = "muffins_mobvote2023";

    public static final AttributeModifier CRAB_REACH = new AttributeModifier(UUID.fromString("9b61c8ad-e259-4a36-b6f6-382ab3c509ea"),"Range Modifier",1.0d,AttributeModifier.Operation.ADDITION);

    public static void init() {
        ModBlocks.BLOCK.init();
        ModEntityTypes.ENTITY_TYPE.init();
        ModFluid.FLUID.init();
        ModItems.ITEMS.init();
        ModMobEffects.MOB_EFFECT.init();
        ModPotions.POTION.init();
    }

    public static void postInit(){
        ModEntityTypes.postInit();
        ModPotions.postInit();
        ModCauldronInteraction.init();
    }
}
