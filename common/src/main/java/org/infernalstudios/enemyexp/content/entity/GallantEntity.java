package org.infernalstudios.enemyexp.content.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.infernalstudios.enemyexp.EEMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class GallantEntity extends Monster implements GeoEntity {

    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");
    private static final RawAnimation SHIELD = RawAnimation.begin().thenPlay("shielding");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private boolean isAttackerInWater;
    private boolean isAttacking;

    public GallantEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ARMOR, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 3.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, false, false));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.5D;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.IRON_GOLEM_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (isAttacking) return false;
        isAttacking = true;

        triggerAnim("Attack", "attack");

        EEMod.scheduleTask((ServerLevel) level(), 12, () -> {
            if (isDeadOrDying()) {
                isAttacking = false;
                return;
            }

            super.doHurtTarget(entity);

            aoeSweep(this);

            this.level().playSound(null, this.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE,
                    1.0F, 0.0F);

            EEMod.scheduleTask((ServerLevel) level(), 8, () -> {
                if (isDeadOrDying()) {
                    isAttacking = false;
                    return;
                }

                aoeSweep(this);
                this.level().playSound(null, this.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE,
                        1.0F, 0.0F);

                isAttacking = false;
            });
        });

        return false;
    }

    private void aoeSweep(Entity entity, float damage, double radius) {
        AABB area = entity.getBoundingBox().inflate(radius);
        List<LivingEntity> nearby = this.level().getEntitiesOfClass(
                LivingEntity.class,
                area,
                e -> e != this && e.isAlive() && !e.isAlliedTo(this));

        // aoe
        for (LivingEntity victim : nearby) {
            if (victim == entity) continue;
            if (!this.hasLineOfSight(victim)) continue;

            victim.hurt(this.damageSources().mobAttack(this), damage);
        }
    }

    private void aoeSweep(Entity entity) {
        aoeSweep(entity, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE), 3.0D);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide()) return false;

        if (source.getEntity() instanceof Player player && player.isCreative()) return super.hurt(source, amount);

        if (source == this.damageSources().lava()) return false;
        if (source == this.damageSources().drown()) return false;
        if (source == this.damageSources().fall()) return false;
        if (source == this.damageSources().cactus()) return false;
        if (source == this.damageSources().wither()) return false;
        if (source.type() == this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(DamageTypes.WITHER_SKULL))
            return false; // if there's a better way to do this LET ME KNOW!!

        // raise shield
        if ((source.getDirectEntity() instanceof LivingEntity) && level().random.nextDouble() <= 0.75D) {
            if (isAttacking) {
                return super.hurt(source, amount);
            }

            if (source.getEntity() instanceof Player player && player.isCreative()) return false;

            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 2, false, false));
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 14, 1, false, false));

            this.level().playSound(null, this.blockPosition(), SoundEvents.BLAZE_HURT, SoundSource.HOSTILE, 1.0F, 0.0F);

            triggerAnim("Attack", "shielding");

            EEMod.scheduleTask((ServerLevel) level(), 16, () -> {
                if (isDeadOrDying()) return;

                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12, 2, false, false));
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 1, false, false));

                EEMod.scheduleTask((ServerLevel) level(), 6, () -> {
                    if (isDeadOrDying()) return;

                    AABB area = getBoundingBox().inflate(3);
                    List<LivingEntity> nearby = this.level().getEntitiesOfClass(
                            LivingEntity.class,
                            area,
                            e -> e != this && e.isAlive() && !e.isAlliedTo(this));

                    if (!nearby.isEmpty()) {
                        aoeSweep(this, isAttackerInWater ? 8.0F : 14.0F, isAttackerInWater ? (1.5D * ((double) 5 /9)) : 1.5D);
                    }

                    isAttackerInWater = false;
                });
            });

            this.isAttackerInWater = source.getDirectEntity().isInWater();
        }

        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) return;
        if (!this.isAlive()) return;

        if (this.isInWaterRainOrBubble()) {
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false));
        }

        if (this.getHealth() < 12.0F) {
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0, false, false));
        }

        if (this.getHealth() < 6.0F) {
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Walk/Idle", 4, state -> {
            if (state.isMoving()) return state.setAndContinue(WALK);
            return state.setAndContinue(IDLE);
        }));

        controllers.add(new AnimationController<>(this, "Attack", 0, state -> PlayState.STOP)
                .triggerableAnim("attack", ATTACK)
                .triggerableAnim("shielding", SHIELD));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
