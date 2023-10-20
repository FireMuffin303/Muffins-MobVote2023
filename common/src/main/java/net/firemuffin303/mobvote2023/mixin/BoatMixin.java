package net.firemuffin303.mobvote2023.mixin;

import net.firemuffin303.mobvote2023.common.registry.ModMobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Boat.class)
public abstract class BoatMixin extends Entity implements VariantHolder<Boat.Type> {

    @Shadow @Nullable public abstract LivingEntity getControllingPassenger();

    public BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    @ModifyArg(method = "controlBoat",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/vehicle/Boat;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    public Vec3 controlInit(Vec3 par1){
        if(this.getControllingPassenger() != null){
            if(this.getControllingPassenger().hasEffect(ModMobEffects.PENGUINS_JET.get())){
                int i = this.getControllingPassenger().getEffect(ModMobEffects.PENGUINS_JET.get()).getAmplifier();
                return par1.multiply(((i+0.5f)*0.075)+1,1,((i+0.5f)*0.075)+1);
            }
        }
        return par1;
    }
}
