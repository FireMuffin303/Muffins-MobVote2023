package net.firemuffin303.mobvote2023.common.entity;

import net.firemuffin303.mobvote2023.common.registry.ModEntityTypes;
import net.firemuffin303.mobvote2023.common.registry.ModItems;
import net.firemuffin303.mobvote2023.common.registry.ModMobEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class Penguin extends Animal {
    private static final Ingredient FOOD_ITEMS;
    static final TargetingConditions SWIM_WITH_PLAYER_TARGETING;
    private static final EntityDataAccessor<Boolean> HAS_EGG;
    public int eggTime;

    public Penguin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.TRAPDOOR, -1.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this,20);
        this.setMaxUpStep(1.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EGG, false);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setHasEgg(compoundTag.getBoolean("HasEgg"));
    }

    public void travel(Vec3 arg) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), arg);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(arg);
        }

    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new PenguinBreedGoal(this,1.0d));
        this.goalSelector.addGoal(1, new PenguinSwimWithBoats(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new AvoidEntityGoal(this, Guardian.class, 8.0F, 1.0D, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Guardian.class})).setAlertOthers(new Class[0]));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 1.25d);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(!this.level().isClientSide()){
            if(this.isAlive() && !this.isBaby() && this.hasEgg() && this.eggTime > 0){
                if(--this.eggTime <=0){
                    ServerLevel serverLevel = (ServerLevel) this.level();
                    Penguin penguin = ModEntityTypes.PENGUIN.get().create(serverLevel);
                    if(penguin != null){
                        penguin.setBaby(true);
                        penguin.moveTo(this.position());
                        penguin.setPersistenceRequired();
                        serverLevel.addFreshEntity(penguin);
                    }
                    this.setHasEgg(false);
                }
            }
        }
    }

    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    public float getWalkTargetValue(BlockPos arg, LevelReader arg2) {
        return 0.0F;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return FOOD_ITEMS.test(itemStack);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntityTypes.PENGUIN.get().create(serverLevel);
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    void setHasEgg(boolean bl) {
        this.entityData.set(HAS_EGG, bl);
    }

    public boolean hasEgg() {
        return (Boolean)this.entityData.get(HAS_EGG);
    }


    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(ModItems.PENGUIN_FEATHER.get(), 1);
        }

    }

    static {
        HAS_EGG = SynchedEntityData.defineId(Penguin.class, EntityDataSerializers.BOOLEAN);
        FOOD_ITEMS = Ingredient.of(new ItemLike[]{Items.COD, Items.SALMON,Items.TROPICAL_FISH});
        SWIM_WITH_PLAYER_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();

    }

    static class PenguinBreedGoal extends BreedGoal{
        private final Penguin penguin;

        public PenguinBreedGoal(Penguin penguin, double d) {
            super(penguin, d);
            this.penguin = penguin;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.penguin.hasEgg();
        }

        @Override
        protected void breed() {
            ServerPlayer serverPlayer = this.animal.getLoveCause();
            if (serverPlayer == null && this.partner.getLoveCause() != null) {
                serverPlayer = this.partner.getLoveCause();
            }

            if (serverPlayer != null) {
                serverPlayer.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this.animal, this.partner, (AgeableMob)null);
            }

            this.penguin.setHasEgg(true);
            this.penguin.eggTime = this.penguin.random.nextInt(100) + 100;

            this.animal.setAge(6000);
            this.partner.setAge(6000);
            this.animal.resetLove();
            this.partner.resetLove();
            RandomSource randomSource = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomSource.nextInt(7) + 1));
            }

        }
    }

    static class PenguinPathNavigation extends AmphibiousPathNavigation {
        PenguinPathNavigation(Penguin penguin, Level level) {
            super(penguin, level);
        }

        public boolean canCutCorner(BlockPathTypes blockPathTypes) {
            return blockPathTypes != BlockPathTypes.WATER_BORDER && super.canCutCorner(blockPathTypes);
        }

        protected PathFinder createPathFinder(int i) {
            this.nodeEvaluator = new PenguinNodeEvaluator(true);
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, i);
        }
    }

    private static class PenguinNodeEvaluator extends AmphibiousNodeEvaluator {
        private final BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

        public PenguinNodeEvaluator(boolean bl) {
            super(bl);
        }

        public Node getStart() {
            return !this.mob.isInWater() ? super.getStart() : this.getStartNode(new BlockPos(Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY), Mth.floor(this.mob.getBoundingBox().minZ)));
        }

        public BlockPathTypes getBlockPathType(BlockGetter blockGetter, int i, int j, int k) {
            this.belowPos.set(i, j - 1, k);
            BlockState blockState = blockGetter.getBlockState(this.belowPos);
            return blockState.is(BlockTags.FROG_PREFER_JUMP_TO) ? BlockPathTypes.OPEN : super.getBlockPathType(blockGetter, i, j, k);
        }
    }

    static class PenguinSwimWithBoats extends FollowBoatGoal{
        @Nullable
        private Player player;
        private final Penguin penguin;


        public PenguinSwimWithBoats(Penguin penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        public boolean canContinueToUse() {
            return this.player != null && this.player.isPassenger() && (Mth.abs(this.player.xxa) > 0.0F || Mth.abs(this.player.zza) > 0.0F) && super.canContinueToUse();
        }

        public void start() {
            super.start();
            List<Boat> list = this.penguin.level().getEntitiesOfClass(Boat.class, this.penguin.getBoundingBox().inflate(5.0D));
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                Boat boat = (Boat)var2.next();
                if (boat.getControllingPassenger() != null && boat.getControllingPassenger() instanceof Player) {
                    this.player = (Player)boat.getControllingPassenger();
                    break;
                }
            }
        }

        @Override
        public boolean canUse() {

            List<Boat> list = this.penguin.level().getEntitiesOfClass(Boat.class, this.penguin.getBoundingBox().inflate(5.0D));
            boolean bl = false;
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Boat boat = (Boat)var3.next();
                Entity entity = boat.getControllingPassenger();
                if (entity instanceof Player && (Mth.abs(((Player)entity).xxa) > 0.0F || Mth.abs(((Player)entity).zza) > 0.0F)) {
                    bl = true;
                    break;
                }
            }

            return this.player != null && (Mth.abs(this.player.xxa) > 0.0F || Mth.abs(this.player.zza) > 0.0F) || bl && super.canUse();


        }

        @Override
        public void tick() {
            super.tick();
            if (this.player != null && this.player.level().random.nextInt(6) == 0) {
                this.player.addEffect(new MobEffectInstance(ModMobEffects.PENGUINS_JET.get(), 100), this.penguin);
            }
        }

        public void stop() {
            super.stop();
            this.player = null;
        }
    }

    static class PenguinSwimWithPlayerGoal extends Goal {
        private final Penguin penguin;
        private final double speedModifier;
        @Nullable
        private Player player;

        PenguinSwimWithPlayerGoal(Penguin penguin, double d) {
            this.penguin = penguin;
            this.speedModifier = d;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            this.player = this.penguin.level().getNearestPlayer(Penguin.SWIM_WITH_PLAYER_TARGETING, this.penguin);
            if (this.player == null) {
                return false;
            } else {
                return this.player.isSwimming() && this.penguin.getTarget() != this.player;
            }
        }

        public boolean canContinueToUse() {
            return this.player != null && this.player.isSwimming() && this.penguin.distanceToSqr(this.player) < 256.0D;
        }

        public void start() {
            this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), this.penguin);
        }

        public void stop() {
            this.player = null;
            this.penguin.getNavigation().stop();
        }

        public void tick() {
            this.penguin.getLookControl().setLookAt(this.player, (float)(this.penguin.getMaxHeadYRot() + 20), (float)this.penguin.getMaxHeadXRot());
            if (this.penguin.distanceToSqr(this.player) < 6.25D) {
                this.penguin.getNavigation().stop();
            } else {
                this.penguin.getNavigation().moveTo(this.player, this.speedModifier);
            }

            if (this.player.isSwimming() && this.player.level().random.nextInt(6) == 0) {
                this.player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100), this.penguin);
            }

        }
    }
}
