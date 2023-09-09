package net.skteam.mumodpack.entity.client;

import net.skteam.mumodpack.mumodpack;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChampBRenderer extends GeoEntityRenderer<ChampBEntity> {
    public ChampBRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChampBModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public Identifier getTextureResource(ChampBEntity instance) {
        return new Identifier(mumodpack.MOD_ID, "textures/entity/champb_texture.png");
    }

    @Override
    public RenderLayer getRenderType(ChampBEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        stack.scale(0.8f, 0.8f, 0.8f);

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
