package net.skteam.mumodpack.entity.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.skteam.mumodpack.entity.custom.BhoundEntity;
import net.skteam.mumodpack.mumodpack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BhoundRender  extends GeoEntityRenderer<BhoundEntity> {
    public BhoundRender(EntityRendererFactory.Context ctx) {
        super(ctx, new BhoundModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public Identifier getTextureResource(BhoundEntity instance) {
        return new Identifier(mumodpack.MOD_ID, "textures/entity/bhound_texture.png");
    }

    @Override
    public RenderLayer getRenderType(BhoundEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        stack.scale(0.8f, 0.8f, 0.8f);

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
