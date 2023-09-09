package net.skteam.mumodpack.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.skteam.mumodpack.mumodpack;

public class ParticleRegistry {
    
    public static final DefaultParticleType NIGHTFALL_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType DAZZLING_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType PURPLE_FLAME = FabricParticleTypes.simple();

    public static void init() {
        registerParticle(NIGHTFALL_PARTICLE, "nightfall_particle");
        registerParticle(DAZZLING_PARTICLE, "dazzling_particle");
        registerParticle(PURPLE_FLAME, "purple_flame");
    }

    public static <I extends DefaultParticleType> I registerParticle(I particle, String name) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(mumodpack.MOD_ID, name), particle);
    }
}
