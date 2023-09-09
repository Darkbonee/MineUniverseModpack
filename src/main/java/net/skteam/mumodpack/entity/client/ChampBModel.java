package net.skteam.mumodpack.entity.client;

import net.skteam.mumodpack.mumodpack;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChampBModel extends AnimatedGeoModel<ChampBEntity> {
    @Override
    public Identifier getModelResource(ChampBEntity object) {
        return new Identifier(mumodpack.MOD_ID, "geo/champb.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChampBEntity object) {
        return new Identifier(mumodpack.MOD_ID, "textures/entity/champb_texture.png");
    }

    @Override
    public Identifier getAnimationResource(ChampBEntity animatable) {
        return new Identifier(mumodpack.MOD_ID, "animations/champb.animation.json");
    }
}
