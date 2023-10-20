package net.firemuffin303.mobvote2023.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.firemuffin303.mobvote2023.MobVote2023;
import net.firemuffin303.mobvote2023.common.block.CrabEggBlock;
import net.firemuffin303.mobvote2023.common.block.SeafoodCauldronBlock;
import net.firemuffin303.mobvote2023.common.fluid.ModLiquid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCK = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, MobVote2023.MOD_ID);
    public static final RegistryEntry<Block> CRAB_EGG = BLOCK.register("crab_egg",() -> new CrabEggBlock(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).noOcclusion()));
    public static final RegistryEntry<Block> SEAFOOD = BLOCK.register("seafood",() -> new ModLiquid(ModFluid.SEAFOOD.get(),BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryEntry<Block> SEAFOOD_CAULDRON = BLOCK.register("seafood_cauldron",() -> new SeafoodCauldronBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));


}
