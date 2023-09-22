package net.skteam.mumodpack.entity.client;

import net.minecraft.util.Identifier;
import net.skteam.mumodpack.entity.custom.BhoundEntity;
import net.skteam.mumodpack.mumodpack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BhoundModel extends AnimatedGeoModel<BhoundEntity> {

    @Override
    public Identifier getModelResource(BhoundEntity object) {
        return new Identifier(mumodpack.MOD_ID, "geo/bhound.geo.json");
    }

    @Override
    public Identifier getTextureResource(BhoundEntity object) {
        return new Identifier(mumodpack.MOD_ID, "textures/entity/bhound_texture.png");
    }

    @Override
    public Identifier getAnimationResource(BhoundEntity animatable) {
        return new Identifier(mumodpack.MOD_ID, "animations/bhound.animation.json");
    }
}
