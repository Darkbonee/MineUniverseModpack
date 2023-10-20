package net.skteam.mumodpack.entity.client;

import net.minecraft.util.Identifier;
import net.skteam.mumodpack.entity.custom.BscoutEntity;
import net.skteam.mumodpack.mumodpack;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BscoutModel extends AnimatedGeoModel<BscoutEntity> {
	@Override
	public Identifier getModelResource(BscoutEntity object) {
		return new Identifier(mumodpack.MOD_ID, "geo/bscout.geo.json");
	}

	@Override
	public Identifier getTextureResource(BscoutEntity object) {
		return new Identifier(mumodpack.MOD_ID, "textures/entity/bscout_texture.png");
	}

	@Override
	public Identifier getAnimationResource(BscoutEntity animatable) {
		return new Identifier(mumodpack.MOD_ID, "animations/bscout.animation.json");
	}
}
