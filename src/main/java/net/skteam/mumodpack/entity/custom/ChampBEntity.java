package net.skteam.mumodpack.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.skteam.mumodpack.config.ConfigConstructor;
import net.skteam.mumodpack.entity.ai.ChampBAttackGoal;
import net.skteam.mumodpack.registry.ItemRegistry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ChampBEntity extends BossEntity implements IAnimatable, IAnimationTickable {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int deathTicks;
    private static final TrackedData<Integer> ATTACKS = DataTracker.registerData(ChampBEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public ChampBEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, BossBar.Color.RED);
        setDrops(ItemRegistry.BOSS_AXE);
    }

    public boolean isFireImmune() {
        return true;
    }

    private <E extends IAnimatable> PlayState attackAnimations(AnimationEvent<E> event) {
        switch (this.getAttackAnimation()) {
            case BOMB ->
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.throwbomb"));
            case HAND_ATTACK ->
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.lowmeleehit"));
            case SPIN ->
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.whirlwind"));
            case JUMP ->
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.jumpslash"));
            case DEATH ->
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.dying"));
            case WALK ->
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.walk"));
            default -> {
                if (event.isMoving()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.walk", ILoopType.EDefaultLoopTypes.LOOP));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.champb.idle", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }
        }
        return PlayState.CONTINUE;
    }



    @Override
    public int getTicksUntilDeath() {
        return 31;
    }

    @Override
    public int getDeathTicks() {
        return this.deathTicks;
    }

    @Override
    public void updatePostDeath() {
        this.deathTicks++;
        if (this.deathTicks >= this.getTicksUntilDeath() && !this.world.isClient) {
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new ChampBAttackGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.5D));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(5, (new RevengeGoal(this)).setGroupRevenge());
        super.initGoals();
    }

    public static DefaultAttributeContainer.Builder createBanditAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 60D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, ConfigConstructor.champb_health)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 5.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKS, 5);
    }

    public void setAttackAnimation(ChampBAnimations attack) {
        for (int i = 0; i < ChampBAnimations.values().length; i++) {
            if (ChampBAnimations.values()[i].equals(attack)) {
                this.dataTracker.set(ATTACKS, i);
            }
        }
    }

    public ChampBAnimations getAttackAnimation() {
        return ChampBAnimations.values()[this.dataTracker.get(ATTACKS)];
    }

    @Override
    public void setDeath() {
        this.setAttackAnimation(ChampBAnimations.DEATH);
    }

    @Override
    public boolean isUndead() {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ILLAGER;
    }

    public boolean disablesShield() {
        return true;
    }

    @Override
    public int tickTimer() {
        return age;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "attacks", 0, this::attackAnimations));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected SoundEvent getAmbientSound() {return SoundEvents.ENTITY_PILLAGER_AMBIENT;}

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }

    public static enum ChampBAnimations {
        JUMP,
        BOMB,
        SPIN,
        HAND_ATTACK,
        DEATH,
        IDLE,
        WALK
    }
}
