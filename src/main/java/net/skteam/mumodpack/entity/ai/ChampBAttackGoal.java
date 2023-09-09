package net.skteam.mumodpack.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.skteam.mumodpack.config.ConfigConstructor;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import net.skteam.mumodpack.entity.custom.ChampBEntity.ChampBAnimations;
import net.skteam.mumodpack.registry.PacketRegistry;
import net.skteam.mumodpack.util.CustomDamageSource;
import net.skteam.mumodpack.util.ParticleNetworking;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

import static net.skteam.mumodpack.entity.custom.ChampBEntity.ChampBAnimations.JUMP;

public class ChampBAttackGoal extends Goal {
    private final ChampBEntity boss;
    private int attackCooldown;
    private int attackStatus;
    private BlockPos attackPos;
    private boolean cordsRegistered;
    private int targetNotVisibleTicks;
    private int specialCooldown;

    public ChampBAttackGoal(ChampBEntity boss) {
        this.boss = boss;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity target = this.boss.getTarget();
        return target != null && target.isAlive() && this.boss.canTarget(target);
    }
    public void resetAttackCooldown(float cooldownModifier) {
        this.attackStatus = 0;
        this.cordsRegistered = false;
        this.attackCooldown = (int) Math.floor(ConfigConstructor.champb_attack_cooldown_ticks * cooldownModifier) - this.boss.getReducedCooldownAttackers();
    }
    public void resetSpecialCooldown(float cooldownModifier) {
        this.specialCooldown = (int) (Math.floor(ConfigConstructor.champb_special_cooldown_ticks * cooldownModifier) - this.boss.getReducedCooldownAttackers());
    }

    public float getModifiedDamage(float damage) {
        return damage * ConfigConstructor.champb_damage_modifier;
    }

    private void damageTarget(LivingEntity target, DamageSource source, float amount) {
        if (target.damage(source, this.getModifiedDamage(amount))) {
            this.boss.heal(this.getModifiedDamage(amount) / 5 + this.boss.getAttackers().size() * 2);
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.boss.setAttackAnimation(ChampBAnimations.IDLE);
        this.attackCooldown = 10;
        this.attackStatus = 0;
        this.cordsRegistered = false;
    }

    public void checkAndSetAttack(@Nullable ChampBAnimations specificAttack, LivingEntity target) {
        if (target == null || (specificAttack != null && specificAttack.equals(ChampBAnimations.IDLE))) {
            this.boss.setAttackAnimation(specificAttack);
            return;
        }
        int rand = this.boss.getRandom().nextInt(ChampBAnimations.values().length);
        ChampBAnimations attack = ChampBAnimations.values()[rand];
        if (specificAttack != null) {
            attack = specificAttack;
        } else if (attack.equals(ChampBAnimations.DEATH) || attack.equals(ChampBAnimations.IDLE) || attack.equals(ChampBAnimations.WALK)){
            attack = JUMP;
        }
        double distanceToEntity = this.boss.squaredDistanceTo(target);
        switch (attack) {
            case BOMB -> {
                if (distanceToEntity < this.getFollowRange() * this.getFollowRange()) {
                    this.boss.setAttackAnimation(attack);
                }
            }
            case HAND_ATTACK, SPIN -> {
                if (distanceToEntity < 20f && this.specialCooldown < 0) {
                    this.boss.setAttackAnimation(attack);
                } else if (this.attackCooldown < -30 || this.specialCooldown > 10) {
                    this.boss.setAttackAnimation(ChampBAnimations.IDLE);
                }
            }
            case JUMP -> {
                if (distanceToEntity < 20f && !this.cordsRegistered && target.getBlockPos() != null) {
                    this.attackPos = target.getBlockPos();
                    this.cordsRegistered = true;
                    this.boss.setAttackAnimation(attack);
                } else if (this.attackCooldown < -30) {
                    this.boss.setAttackAnimation(ChampBAnimations.IDLE);
                }
            }
            default -> this.boss.setAttackAnimation(ChampBAnimations.IDLE);
        }
    }

    private double getFollowRange() {
        return this.boss.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
    }

    public void tick() {
        this.attackCooldown--;
        this.specialCooldown--;

        LivingEntity target = this.boss.getTarget();
        if (target != null && !this.boss.getAttackAnimation().equals(ChampBAnimations.DEATH)) {
            this.boss.setAttacking(true);
            this.boss.getLookControl().lookAt(target.getX(), target.getEyeY(), target.getZ());
            boolean entityInSight = this.boss.getVisibilityCache().canSee(target);
            if (entityInSight) {
                this.targetNotVisibleTicks = 0;
            } else {
                ++this.targetNotVisibleTicks;
            }
            double distanceToEntity = this.boss.squaredDistanceTo(target);
            if (this.attackCooldown > 0) this.boss.setAttackAnimation(ChampBAnimations.IDLE);

            if (this.attackCooldown < 0 && this.boss.getAttackAnimation().equals(ChampBAnimations.IDLE)) {
                this.checkAndSetAttack(null, target);
            }
            switch (this.boss.getAttackAnimation()) {
                case BOMB -> this.projectileBarrage(target, distanceToEntity, BarrageProjectiles.BOMB);
                case SPIN -> this.spinAttack();
                case JUMP -> this.jump();
                case HAND_ATTACK -> this.handattack();
                default -> this.boss.setAttackAnimation(ChampBAnimations.IDLE);
            }

              if (this.targetNotVisibleTicks < 5) {
                  this.boss.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(), 0.5D);
              }
        super.tick();
        }
    }

    private void handattack() {
        this.attackStatus++;
        if (this.attackStatus == 13 || this.attackStatus == 18 || this.attackStatus == 20) {
            if (!this.boss.world.isClient) ParticleNetworking.sendServerParticlePacket((ServerWorld) this.boss.world, PacketRegistry.CHAMPB_JAMP_PACKET_ID, this.boss.getBlockPos(), 200);
            Box chunkBox = new Box(this.boss.getBlockPos()).expand(3);
            List<Entity> nearbyEntities = this.boss.world.getOtherEntities(this.boss, chunkBox);
            for (Entity nearbyEntity : nearbyEntities) {
                if (nearbyEntity instanceof LivingEntity closestTarget) {
                    double x = closestTarget.getX() - (this.boss.getX());
                    double z = closestTarget.getZ() - this.boss.getZ();
                    this.damageTarget(closestTarget, DamageSource.mob(this.boss), 8f);
                    closestTarget.takeKnockback(0.2F, -x, -z);
                }
            }
            this.boss.world.playSound(null, this.boss.getBlockPos(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.HOSTILE, 1f, 1f);
        }
        if (this.attackStatus >= 21) {
            this.resetAttackCooldown(.5f);
            this.resetSpecialCooldown(1.2f);
            this.checkAndSetAttack(null, this.boss.getTarget());
        }
    }

    private void spinAttack() {
        this.attackStatus++;
        if (this.attackStatus >= 7 && this.attackStatus <= 28) {
            Box chunkBox = new Box(this.boss.getBlockPos()).expand(3);
            List<Entity> nearbyEntities = this.boss.world.getOtherEntities(this.boss, chunkBox);
            for (Entity nearbyEntity : nearbyEntities) {
                if (nearbyEntity instanceof LivingEntity closestTarget) {
                    double x = closestTarget.getX() - (this.boss.getX());
                    double z = closestTarget.getZ() - this.boss.getZ();
                    this.damageTarget(closestTarget, DamageSource.mob(this.boss), 6f);
                    closestTarget.takeKnockback(0.5F, -x, -z);
                }
            }
        }
        if (this.attackStatus >= 28) {
            this.resetAttackCooldown(.5f);
            this.resetSpecialCooldown(1.2f);
            this.checkAndSetAttack(null, this.boss.getTarget());
        }
    }

    private void jump() {
        this.attackStatus++;
        this.boss.getLookControl().lookAt(this.attackPos.getX(), this.attackPos.getY(), this.attackPos.getZ());
        this.boss.getNavigation().startMovingTo(this.attackPos.getX(), this.attackPos.getY(), this.attackPos.getZ(), 0.5D);
        this.boss.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5, 10));

        Box aoe = new Box(this.attackPos).expand(2);
        List<Entity> entities = this.boss.world.getOtherEntities(this.boss, aoe);
        if (this.attackStatus == 20) {
            if (!this.boss.world.isClient) ParticleNetworking.sendServerParticlePacket((ServerWorld) this.boss.world, PacketRegistry.CHAMPB_JAMP_PACKET_ID, this.attackPos, 200);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity) {
                    this.damageTarget((LivingEntity) entity, CustomDamageSource.obliterateDamageSource(this.boss), 19f);
                    entity.setVelocity(entity.getVelocity().x, .3f, entity.getVelocity().z);
                }
            }
            this.boss.world.playSound(null, this.attackPos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.5f, 1f);
        }
        if (this.attackStatus >= 20) {
            this.resetAttackCooldown(.5f);
            this.cordsRegistered = false;
            this.checkAndSetAttack(null, this.boss.getTarget());
        }
    }
    private void projectileBarrage(LivingEntity target, double distanceToEntity, BarrageProjectiles entity) {
        this.boss.getMoveControl().moveTo(target.getX(), target.getY(), target.getZ(), 0.0D);
        int fireSprayCount = 1;
        this.attackStatus++;
        double e = target.getX() - (this.boss.getX());
        double f = target.getBodyY(0.5D) - this.boss.getBodyY(1.0D);
        double g = target.getZ() - this.boss.getZ();
        if (this.attackStatus % 2 == 0 && this.attackStatus > 8) {
            double h = Math.sqrt(Math.sqrt(distanceToEntity)) * 0.5D;
            this.boss.world.playSound(null, this.boss.getBlockPos(), SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 2f, 1f);
            for(int i = 0; i < fireSprayCount; ++i) {
                new SmallFireballEntity(this.boss.world, this.boss, e + this.boss.getRandom().nextGaussian() * h, f, g + this.boss.getRandom().nextGaussian() * h);
                ProjectileEntity projectile = switch (entity) {
                    case BOMB ->
                            new SmallFireballEntity(this.boss.world, this.boss, e + this.boss.getRandom().nextGaussian() * h, f, g + this.boss.getRandom().nextGaussian() * h);
                };
                projectile.setPosition(projectile.getX(), this.boss.getBodyY(2.0D) - 1.5D, projectile.getZ());
                this.boss.world.spawnEntity(projectile);
            }
        }
        if (this.attackStatus >= 18) {
            this.resetAttackCooldown(1);
            this.checkAndSetAttack(null, this.boss.getTarget());
        }
    }

    enum BarrageProjectiles {
        BOMB
    }
}