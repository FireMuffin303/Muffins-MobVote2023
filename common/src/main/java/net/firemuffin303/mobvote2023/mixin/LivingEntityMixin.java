package net.firemuffin303.mobvote2023.mixin;

import net.firemuffin303.mobvote2023.common.registry.ModMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"),method = "canFreeze", cancellable = true)
    public void canFreezeInit(CallbackInfoReturnable<Boolean> cir){
        if(((LivingEntity)(Object)this).hasEffect(ModMobEffects.ICE_RESISTANCE.get())){
            cir.setReturnValue(false);
        }
    }


    @Shadow
    @Nullable
    @Override
    public LivingEntity getLastAttacker() {
        return null;
    }

    @Shadow
    @Override
    protected void defineSynchedData() {

    }
    @Shadow
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Shadow
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
