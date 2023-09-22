package net.skteam.mumodpack.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class BhoundEntity extends WolfEntity implements IAnimatable {

    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final TrackedData<Integer> dog = DataTracker.registerData(BhoundEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BhoundEntity(EntityType<? extends WolfEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::dogAnimations));
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState dogAnimations(AnimationEvent<E> event) {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bhound.idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if(this.isSitting()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bhound.idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bhound.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }
}
