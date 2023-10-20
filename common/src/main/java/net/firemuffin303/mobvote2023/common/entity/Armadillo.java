package net.firemuffin303.mobvote2023.common.entity;

import net.firemuffin303.mobvote2023.common.registry.ModEntityTypes;
import net.firemuffin303.mobvote2023.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Armadillo extends Animal {
    private static final Ingredient FOOD_ITEMS;
    private static final EntityDataAccessor<Boolean> IS_DEFENSE_MODE;


    public Armadillo(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ArmadilloPanicGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_DEFENSE_MODE, false);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsDefenseMode", this.isDefenseMode());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDefenseMode(compoundTag.getBoolean("IsDefenseMode"));
    }



    public boolean isDefenseMode() {
        return (Boolean) this.entityData.get(IS_DEFENSE_MODE);
    }

    public void setDefenseMode(boolean isDefenseMode) {
        this.entityData.set(IS_DEFENSE_MODE, isDefenseMode);
    }

    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(ModItems.ARMADILLO_SCUTE.get(), 1);
        }

    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return FOOD_ITEMS.test(itemStack);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntityTypes.ARMADILLO.get().create(serverLevel);
    }

    static {
        FOOD_ITEMS = Ingredient.of(Items.SPIDER_EYE);
        IS_DEFENSE_MODE = SynchedEntityData.defineId(Armadillo.class, EntityDataSerializers.BOOLEAN);

    }

    static class ArmadilloPanicGoal extends Goal {
        public Armadillo armadillo;
        private Player player;

        public ArmadilloPanicGoal(Armadillo armadillo) {
            this.armadillo = armadillo;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.shouldPanic();
        }

        protected boolean shouldPanic() {
            return this.armadillo.getLastHurtByMob() == this.player || this.armadillo.isFreezing() || this.armadillo.isOnFire();
        }

        public void start() {
            List<Player> list = this.armadillo.level().getEntitiesOfClass(Player.class, this.armadillo.getBoundingBox().inflate(5.0D));
            for(Player playerloop : list){
                if(playerloop != null){
                    this.player = playerloop;
                    break;
                }
            }
        }

        @Override
        public void tick() {
            super.tick();
            if(this.player != null){
                this.armadillo.setDefenseMode(true);
                this.armadillo.getNavigation().stop();
            }
        }

        public boolean canContinueToUse() {

            return this.player != null && this.armadillo.getLastHurtByMob() == this.player;
        }

        @Override
        public void stop() {
            super.stop();
            this.armadillo.setDefenseMode(false);
        }
    }
}
