package net.firemuffin303.mobvote2023.mixin;

import net.firemuffin303.mobvote2023.common.registry.ModItems;
import net.firemuffin303.mobvote2023.common.registry.ModMobEffects;
import net.firemuffin303.mobvote2023.utils.ModPlatform;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if(!this.level().isClientSide){
            this.holdingCrabClawTick();
        }
        //this.crabReachEffectTick();

    }

    public void holdingCrabClawTick(){
        ItemStack stackMainHand = this.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.OFFHAND);
        if (itemStack.is(ModItems.CRAB_CLAW.get()) || stackMainHand.is(ModItems.CRAB_CLAW.get())) {
            ModPlatform.holdingCrabClaw((Player)(Object) this);
        }else{
            ModPlatform.stopHoldingCrabClaw((Player)(Object) this);

        }
    }

    /*public void crabReachEffectTick(){
        if(((Player)(Object)this).hasEffect(ModMobEffects.CRAB_REACH.get())){
            int i = this.getEffect(ModMobEffects.CRAB_REACH.get()).getAmplifier();
            ModPlatform.applyLongReach((Player)(Object)this,i);
        }else{
            ModPlatform.stopApplyLongReach((Player)(Object)this);

        }
    }*/

    @Shadow
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Shadow
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return null;
    }

    @Shadow
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Shadow
    public HumanoidArm getMainArm() {
        return null;
    }
}