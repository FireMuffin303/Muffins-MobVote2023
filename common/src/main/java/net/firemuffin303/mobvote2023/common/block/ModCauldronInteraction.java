package net.firemuffin303.mobvote2023.common.block;

import net.firemuffin303.mobvote2023.common.registry.ModBlocks;
import net.firemuffin303.mobvote2023.common.registry.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;

import java.util.Map;

import static net.minecraft.core.cauldron.CauldronInteraction.*;

public class ModCauldronInteraction {
    static Map<Item, CauldronInteraction> SEAFOOD = newInteractionMap();

    static CauldronInteraction FILL_SEAFOOD = (blockState, level, blockPos, player, interactionHand, itemStack) -> {
        return emptyBucket(level, blockPos, player, interactionHand, itemStack, ModBlocks.SEAFOOD_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL,3), SoundEvents.BUCKET_EMPTY);
    };

    static CauldronInteraction CRAB_MEAT = (blockState, level, blockPos, player, interactionHand, itemStack) -> {
        if(itemStack.is(ModItems.COOKED_CRAB_MEAT.get()) && !level.isClientSide()){
            ItemUtils.createFilledResult(itemStack,player,new ItemStack(ModItems.CRAB_MEAT_WITH_SEAFOOD.get()),level.isClientSide);
            if(blockState.getValue(LayeredCauldronBlock.LEVEL)-1 > 0){
                level.setBlockAndUpdate(blockPos, blockState.setValue(LayeredCauldronBlock.LEVEL,blockState.getValue(LayeredCauldronBlock.LEVEL)-1));
            }else{
                level.setBlockAndUpdate(blockPos,Blocks.CAULDRON.defaultBlockState());

            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    };

    public static void init(){
        CauldronInteraction.addDefaultInteractions(SEAFOOD);
        EMPTY.put(ModItems.SEAFOOD_BUCKET.get(),FILL_SEAFOOD);
        WATER.put(ModItems.SEAFOOD_BUCKET.get(),FILL_SEAFOOD);
        LAVA.put(ModItems.SEAFOOD_BUCKET.get(),FILL_SEAFOOD);
        POWDER_SNOW.put(ModItems.SEAFOOD_BUCKET.get(),FILL_SEAFOOD);

        SEAFOOD.put(ModItems.COOKED_CRAB_MEAT.get(),CRAB_MEAT);
        SEAFOOD.put(Items.BUCKET,(blockState, level, blockPos, player, interactionHand, itemStack) -> {
            return fillBucket(blockState,level,blockPos,player,interactionHand,itemStack,new ItemStack(ModItems.SEAFOOD_BUCKET.get()),(blockState1 -> {
              return (Integer)blockState1.getValue(LayeredCauldronBlock.LEVEL) == 3;
            }),SoundEvents.BUCKET_FILL);
        });
    }
}
