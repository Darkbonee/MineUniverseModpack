package net.skteam.mumodpack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.skteam.mumodpack.client.PacketsClient;
import net.skteam.mumodpack.entity.client.ChampBRenderer;
import net.skteam.mumodpack.registry.EntityRegistry;

public class mumodpackClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.CHAMP_B_ENTITY, ChampBRenderer::new);
        PacketsClient.initClient();
    }
}
