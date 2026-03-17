package com.acorsicanfrog.hungrywaters.entity;

import com.acorsicanfrog.hungrywaters.HungryWatersCommon;
import com.acorsicanfrog.hungrywaters.HungryWatersConfig;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PiranhaEntity extends AbstractFish {

    private static final EntityDataAccessor<Integer> DATA_HUNGER =
            SynchedEntityData.defineId(PiranhaEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_AGGRESSIVE =
            SynchedEntityData.defineId(PiranhaEntity.class, EntityDataSerializers.BOOLEAN);

    private int hungerTickCounter = 0;
    private int retaliationTimer = 0;
    private int biteCounter = 0;

    public final AnimationState swimDefaultAnimationState = new AnimationState();
    public final AnimationState swimAttackAnimationState = new AnimationState();

    private static final int RETALIATION_DURATION = 600; // 30 seconds
    private static final int BITES_TO_SATISFY = 4;

    public PiranhaEntity(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.1F, 0.5F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static boolean checkSpawnRules(EntityType<? extends PiranhaEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getFluidState(pos).is(net.minecraft.tags.FluidTags.WATER)
                && level.getBlockState(pos.above()).getFluidState().is(net.minecraft.tags.FluidTags.WATER);
    }

    private static final ResourceLocation SCALE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_scale");
    private static final ResourceLocation HEALTH_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_health");
    private static final ResourceLocation SPEED_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_speed");
    private static final ResourceLocation DAMAGE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_damage");

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.SCALE, 1.0D)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.0D);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         MobSpawnType spawnType, @Nullable SpawnGroupData groupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, groupData);

        double min = HungryWatersConfig.scaleMin;
        double max = HungryWatersConfig.scaleMax;
        double scale = min + this.random.nextDouble() * (max - min);
        this.getAttribute(Attributes.SCALE).addPermanentModifier(
                new AttributeModifier(SCALE_MODIFIER_ID, scale - 1.0D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
        );

        double healthDiff = HungryWatersConfig.maxHealth - 5.0D;
        if (healthDiff != 0.0D) {
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(
                    new AttributeModifier(HEALTH_MODIFIER_ID, healthDiff, AttributeModifier.Operation.ADD_VALUE)
            );
            this.setHealth(this.getMaxHealth());
        }

        double speedDiff = HungryWatersConfig.movementSpeed - 0.3D;
        if (speedDiff != 0.0D) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(
                    new AttributeModifier(SPEED_MODIFIER_ID, speedDiff, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        double damageDiff = HungryWatersConfig.attackDamage - 2.0D;
        if (damageDiff != 0.0D) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(
                    new AttributeModifier(DAMAGE_MODIFIER_ID, damageDiff, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        if (HungryWatersConfig.randomStartHunger) {
            setHunger(this.random.nextInt(HungryWatersConfig.hungerMax + 1));
        } else {
            setHunger(HungryWatersConfig.hungerMax);
        }

        return data;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HUNGER, HungryWatersConfig.hungerMax);
        builder.define(DATA_AGGRESSIVE, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PiranhaMeleeAttackGoal(this, 3.0D, true));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 40));

        this.targetSelector.addGoal(0, new PiranhaRetaliateGoal(this));
        this.targetSelector.addGoal(1, new PiranhaHuntGoal<>(this, AbstractFish.class, target -> !(target instanceof PiranhaEntity)));
        this.targetSelector.addGoal(2, new PiranhaHuntGoal<>(this, Player.class, target -> !(target instanceof Player p && p.getVehicle() instanceof net.minecraft.world.entity.vehicle.Boat)));
        this.targetSelector.addGoal(3, new PiranhaHuntGoal<>(this, Animal.class, target -> true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.isAggressive()) {
                this.swimDefaultAnimationState.stop();
                this.swimAttackAnimationState.startIfStopped(this.tickCount);

                if (this.isInWater() && this.tickCount % 2 == 0) {
                    Vec3 motion = this.getDeltaMovement();
                    double x = this.getX() + (this.random.nextDouble() - 0.5) * 0.2;
                    double y = this.getY() + this.random.nextDouble() * this.getBbHeight();
                    double z = this.getZ() + (this.random.nextDouble() - 0.5) * 0.2;
                    this.level().addParticle(
                        net.minecraft.core.particles.ParticleTypes.BUBBLE,
                        x, y, z,
                        -motion.x * 0.3,
                        -motion.y * 0.3 + 0.02,
                        -motion.z * 0.3
                    );
                }
            } else {
                this.swimAttackAnimationState.stop();
                this.swimDefaultAnimationState.startIfStopped(this.tickCount);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            drainHunger();
            tickRetaliation();
            setAggressive(this.getTarget() != null);
        }
    }

    private void tickRetaliation() {
        if (retaliationTimer > 0) {
            retaliationTimer--;

            LivingEntity target = this.getTarget();
            if (target != null && !target.isInWater()) {
                retaliationTimer = 0;
                this.setTarget(null);
                return;
            }

            if (retaliationTimer <= 0 && !isHungry()) {
                this.setTarget(null);
            }
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            float speed = this.getTarget() != null ? 0.04F : 0.02F;
            this.moveRelative(speed, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
    }

    private void drainHunger() {
        hungerTickCounter++;
        if (hungerTickCounter >= 20) {
            hungerTickCounter = 0;
            int current = getHunger();
            int drain = HungryWatersConfig.hungerDrainRate;
            setHunger(Math.max(0, current - drain));
        }
    }

    public int getHunger() {
        return this.entityData.get(DATA_HUNGER);
    }

    public void setHunger(int value) {
        this.entityData.set(DATA_HUNGER, value);
    }

    public boolean isHungry() {
        return HungryWatersConfig.alwaysAggressive || getHunger() <= 0;
    }

    public boolean isAggressive() {
        return this.entityData.get(DATA_AGGRESSIVE);
    }

    public void setAggressive(boolean aggressive) {
        this.entityData.set(DATA_AGGRESSIVE, aggressive);
    }

    public void resetHunger() {
        setHunger(HungryWatersConfig.hungerMax);
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result) {
            biteCounter++;
            if ((target instanceof LivingEntity living && living.isDeadOrDying()) || biteCounter >= BITES_TO_SATISFY) {
                resetHunger();
                biteCounter = 0;
            }
        }
        return result;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && source.getEntity() instanceof LivingEntity attacker) {
            this.setTarget(attacker);
            this.retaliationTimer = RETALIATION_DURATION;
        }
        return result;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Hunger", getHunger());
        compound.putInt("HungerTickCounter", hungerTickCounter);
        compound.putInt("RetaliationTimer", retaliationTimer);
        compound.putInt("BiteCounter", biteCounter);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Hunger")) {
            setHunger(compound.getInt("Hunger"));
        }
        if (compound.contains("HungerTickCounter")) {
            hungerTickCounter = compound.getInt("HungerTickCounter");
        }
        if (compound.contains("RetaliationTimer")) {
            retaliationTimer = compound.getInt("RetaliationTimer");
        }
        if (compound.contains("BiteCounter")) {
            biteCounter = compound.getInt("BiteCounter");
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(HungryWatersCommon.PIRANHA_BUCKET_ITEM.get());
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(Items.BUCKET) && this.isAlive() && this.isInWater()) {
            this.playSound(this.getPickupSound(), 1.0F, 1.0F);
            ItemStack bucketStack = this.getBucketItemStack();
            this.saveToBucketTag(bucketStack);
            ItemStack result = ItemUtils.createFilledResult(held, player, bucketStack, false);
            player.setItemInHand(hand, result);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketStack);
            }
            this.discard();
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        super.saveToBucketTag(bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, tag -> tag.putInt("Hunger", getHunger()));
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        super.loadFromBucketTag(tag);
        if (tag.contains("Hunger")) {
            setHunger(tag.getInt("Hunger"));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    static class PiranhaMeleeAttackGoal extends MeleeAttackGoal {
        public PiranhaMeleeAttackGoal(PiranhaEntity mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            return this.mob.getTarget() != null && super.canUse();
        }
    }

    static class PiranhaRetaliateGoal extends HurtByTargetGoal {
        public PiranhaRetaliateGoal(PiranhaEntity piranha) {
            super(piranha);
        }

        @Override
        public boolean canContinueToUse() {
            PiranhaEntity piranha = (PiranhaEntity) this.mob;
            if (piranha.retaliationTimer <= 0) {
                return false;
            }
            LivingEntity target = this.mob.getTarget();
            if (target != null && !target.isInWater()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    static class PiranhaHuntGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public PiranhaHuntGoal(PiranhaEntity piranha, Class<T> targetType, java.util.function.Predicate<LivingEntity> extraFilter) {
            super(piranha, targetType, 10, true, false,
                    target -> target.isInWater() && extraFilter.test(target));
        }

        @Override
        public boolean canUse() {
            PiranhaEntity piranha = (PiranhaEntity) this.mob;
            if (!piranha.isHungry()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            PiranhaEntity piranha = (PiranhaEntity) this.mob;
            if (!piranha.isHungry()) {
                return false;
            }
            LivingEntity target = this.mob.getTarget();
            if (target != null && !target.isInWater()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }
}
