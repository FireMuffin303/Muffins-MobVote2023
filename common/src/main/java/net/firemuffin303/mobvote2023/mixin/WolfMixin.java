package net.firemuffin303.mobvote2023.mixin;

import net.firemuffin303.mobvote2023.common.item.WolfArmorItem;
import net.firemuffin303.mobvote2023.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import java.util.function.Predicate;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal implements NeutralMob, ContainerListener {
    @Shadow private float interestedAngle;



    @Shadow protected abstract void playStepSound(BlockPos blockPos, BlockState blockState);

    protected SimpleContainer inventory;
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("67854898-a509-406b-b997-57efa8089988");

    protected WolfMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }



    @Inject(at = @At("HEAD"),method= "mobInteract", cancellable = true)
    public void mobInteractedInit(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir){
        ItemStack itemStack = player.getMainHandItem();
        if(!this.level().isClientSide()){
            if(((Wolf)(Object)this).isTame()){
                if(this.isArmor(itemStack) && !this.isWearingArmor()){
                    this.inventory.setItem(0,itemStack.copyWithCount(1));
                    this.playSound(SoundEvents.ARMOR_EQUIP_IRON);
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }

                if(player.isShiftKeyDown() && player.getMainHandItem().isEmpty() && this.isWearingArmor()){
                    player.setItemInHand(InteractionHand.MAIN_HAND,this.inventory.getItem(0).copyWithCount(1));
                    this.inventory.setItem(0,new ItemStack(Items.AIR));
                    this.playSound(SoundEvents.ARMOR_EQUIP_IRON);
                    cir.setReturnValue(InteractionResult.SUCCESS);

                }
            }

        }

    }

    @Inject(at = @At("TAIL"), method = "<init>")
    protected void init(EntityType<? extends Wolf> entityType, Level level, CallbackInfo ci) {
        this.createInventory();
    }

    public void createInventory(){
        SimpleContainer simpleContainer = this.inventory;
        this.inventory = new SimpleContainer(1);
        if (simpleContainer != null) {
            simpleContainer.removeListener(this);
            int i = Math.min(simpleContainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleContainer.getItem(j);
                if (!itemStack.isEmpty()) {
                    this.inventory.setItem(j, itemStack.copy());
                }
            }
        }
        this.inventory.addListener(this);
        this.updateContainerEquipment();
    }


    public boolean isArmor(ItemStack itemStack){
        return itemStack.getItem() instanceof WolfArmorItem;
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if(!this.inventory.getItem(0).isEmpty()){
            compoundTag.put("ArmorItem",this.inventory.getItem(0).save(new CompoundTag()));
        }
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if(compoundTag.contains("ArmorItem")){
            ItemStack itemStack = ItemStack.of(compoundTag.getCompound("ArmorItem"));
            if (!itemStack.isEmpty() && this.isArmor(itemStack)) {
                this.inventory.setItem(0, itemStack);
            }
        }

        this.updateContainerEquipment();
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tickInit(CallbackInfo ci){
        this.wearingTurtleArmor();
    }

    public void wearingTurtleArmor(){
        ItemStack armor = this.inventory.getItem(0);
        if (armor.is(ModItems.AQUA_DOG_ARMOR.get()) && this.isInWater()) {
            this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,200,0,false,false,false));
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,100,0,false,false,false));
        }
    }

    public boolean isWearingArmor() {
        return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
    }

    @Override
    public void containerChanged(Container container) {
        this.updateContainerEquipment();
    }

    private void setArmorEquipment(ItemStack itemStack) {
        this.setArmor(itemStack);
        if (!this.level().isClientSide) {
            this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
            if (this.isArmor(itemStack)) {
                int i = ((WolfArmorItem)itemStack.getItem()).getProtection();
                if (i != 0) {
                    this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Wolf armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlot.CHEST);
    }


    protected void updateContainerEquipment() {
        if (!this.level().isClientSide) {
            this.setArmorEquipment(this.inventory.getItem(0));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    private void setArmor(ItemStack itemStack) {
        this.setItemSlot(EquipmentSlot.CHEST, itemStack);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }
}
