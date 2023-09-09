package net.skteam.mumodpack.client;

import java.util.Random;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.skteam.mumodpack.registry.PacketRegistry;
import net.skteam.mumodpack.registry.ParticleRegistry;

public class PacketsClient {

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.DAWNBREAKER_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            client.execute(() -> {
                DefaultParticleType[] particlesRound = {ParticleTypes.FLAME};
                DefaultParticleType[] particlesOutburst = {ParticleTypes.LARGE_SMOKE, ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.SOUL};
                float[][] velDividers = {{1, 8, 1}, {2, 8, 2}, {2, 8, 2}};
                PacketsClient.roundParticleOutburst(client.world, 1000, particlesRound, target.getX(), target.getY(), target.getZ(), 1f);
                PacketsClient.particleOutburst(client.world, 200, particlesOutburst, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.CHAMPB_JAMP_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                DefaultParticleType[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.ASH, ParticleTypes.SMOKE};
                float[][] velDividers = {{1, 8, 1}, {2, 8, 2}, {2, 8, 2}};
                PacketsClient.particleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.SOUL_RUPTURE_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                DefaultParticleType[] particles = {ParticleTypes.SOUL, ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.LARGE_SMOKE};
                float[][] velDividers = {
                    {8, 2, 8}, {8, 2, 8}, {8, 2, 8}
                };
                PacketsClient.particleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.CONJURE_ENTITY_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                DefaultParticleType[] particles = {ParticleTypes.SOUL, ParticleTypes.DRAGON_BREATH};
                float[][] velDividers = {
                    {8, 2, 8}, {8, 2, 8}
                };
                PacketsClient.particleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.DEATH_EXPLOSION_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            boolean soulFlame = buf.readBoolean();
            client.execute(() -> {
                DefaultParticleType[] particlesRound = {soulFlame ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, ParticleTypes.LARGE_SMOKE};
                PacketsClient.roundParticleOutburst(client.world, 1000, particlesRound, target.getX(), target.getY(), target.getZ(), 1f);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.RANDOM_EXPLOSION_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                PacketsClient.roundParticleOutburst(client.world, points, PacketsClient.randomParticle(), target.getX(), target.getY(), target.getZ(), 1f);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.BIG_TELEPORT_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            DefaultParticleType[] particles = {ParticleTypes.PORTAL};
            client.execute(() -> {
                PacketsClient.roundParticleOutburst(client.world, points, particles, target.getX(), target.getY() + 3, target.getZ(), 4f);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.DRAGON_BREATH_EXPLOSION_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            DefaultParticleType[] particles = {ParticleTypes.DRAGON_BREATH, ParticleTypes.DRAGON_BREATH};
            client.execute(() -> {
                PacketsClient.roundParticleOutburst(client.world, points, particles, target.getX(), target.getY() + 3, target.getZ(), .5f);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.DARK_EXPLOSION_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            DefaultParticleType[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.SMOKE, ParticleTypes.POOF};
            client.execute(() -> {
                PacketsClient.roundParticleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), .25f);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.SNOW_PARTICLES_ID, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            double width = buf.readDouble();
            float height = buf.readFloat();
            client.execute(() -> {
                client.world.addParticle(ParticleTypes.SNOWFLAKE, PacketsClient.getParticleX(0.5D, pos, width), PacketsClient.getParticleY(pos, height), PacketsClient.getParticleZ(0.5D, pos, width), 0.0D, 0.0D, 0.0D);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.ICE_PARTICLES_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            ItemStackParticleEffect[] particles = {new ItemStackParticleEffect(ParticleTypes.ITEM, Items.ICE.getDefaultStack())};
            client.execute(() -> {
                PacketsClient.roundParticleOutburst(client.world, points, particles, target.getX(), target.getY() + .5f, target.getZ(), .75f);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.MOONLIGHT_PARTICLES_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            DefaultParticleType[] particles = {ParticleTypes.SOUL_FIRE_FLAME};
            client.execute(() -> {
                PacketsClient.roundParticleOutburst(client.world, points, particles, target.getX(), target.getY() + .5f, target.getZ(), 1/8f);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.SWORD_SWIPE_ID, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            double eyeHeight = buf.readDouble();
            client.execute(() -> {
                client.world.addParticle(ParticleTypes.SWEEP_ATTACK, true, pos.getX(), eyeHeight, pos.getZ(), 0, 0, 0);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.OBLITERATE_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.SOUL, new ItemStackParticleEffect(ParticleTypes.ITEM, Items.DIRT.getDefaultStack()), new ItemStackParticleEffect(ParticleTypes.ITEM, Items.STONE.getDefaultStack())};
                float[][] velDividers = {{1, 8, 1}, {2, 8, 2}, {2, 8, 2}, {1, 2, 1}, {1, 2, 1}};
                PacketsClient.particleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.GROUND_RUPTURE_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            double x = buf.readDouble();
            float z = buf.readFloat();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.LARGE_SMOKE, new ItemStackParticleEffect(ParticleTypes.ITEM, Items.DIRT.getDefaultStack()), new ItemStackParticleEffect(ParticleTypes.ITEM, Items.STONE.getDefaultStack())};
                float[][] velDividers = {
                    {2, 0.5f, 2}, {2, 0.5f, 2}, {2, 0.5f, 2}
                };
                PacketsClient.particleOutburst(client.world, 200, particles, x, target.getY(), (double)z, velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.BLINDING_LIGHT_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            double eyeHeight = buf.readDouble();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.WAX_OFF, ParticleTypes.SOUL};
                float[][] velDividers = {
                    {0.05f, 0.05f, 0.05f}, {4, 4, 4}
                };
                PacketsClient.particleOutburst(client.world, 100, particles, target.getX(), eyeHeight, target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.GRAND_SKYFALL_SMASH_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            double increasedRange = buf.readDouble();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.FLAME, new ItemStackParticleEffect(ParticleTypes.ITEM, Items.DIRT.getDefaultStack()), new ItemStackParticleEffect(ParticleTypes.ITEM, Items.STONE.getDefaultStack())};
                float[][] velDividers = {{1, 8, 1}, {1, 6, 1}, {1, 2, 1}, {1, 2, 1}};
                PacketsClient.particleOutburst(client.world, 200, particles, target.getX(), target.getY(), target.getZ(), velDividers, increasedRange);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.BLINDING_LIGHT_SMASH_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.FIREWORK, ParticleTypes.WAX_OFF, new ItemStackParticleEffect(ParticleTypes.ITEM, Items.DIRT.getDefaultStack()), new ItemStackParticleEffect(ParticleTypes.ITEM, Items.STONE.getDefaultStack())};
                float[][] velDividers = {{1, 8, 1}, {2, 8, 2}, {2, 8, 2}, {1, 2, 1}, {1, 2, 1}};
                PacketsClient.particleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.SOUL_FLAME_RUPTURE_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            double x = buf.readDouble();
            float z = buf.readFloat();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.SOUL_FIRE_FLAME, ParticleTypes.LARGE_SMOKE};
                float[][] velDividers = {
                    {2, 0.5f, 2}, {2, 0.5f, 2}
                };
                PacketsClient.particleOutburst(client.world, 200, particles, x, target.getY(), (double)z, velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.SOUL_FLAME_OUTBURST_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            double height = buf.readDouble();
            client.execute(() -> {
                DefaultParticleType[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.SOUL_FIRE_FLAME};
                float[][] velDividers = {{3, 3, 3}, {3, 3, 3}};
                PacketsClient.particleOutburst(client.world, 200, particles, target.getX(), height, target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.MJOLNIR_LEVIATHAN_AXE_COLLISION_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            client.execute(() -> {
                DefaultParticleType[] particlesRound = {ParticleRegistry.NIGHTFALL_PARTICLE, ParticleTypes.LARGE_SMOKE};
                DefaultParticleType[] particlesOut = {ParticleTypes.ELECTRIC_SPARK, ParticleTypes.CLOUD, ParticleTypes.SCRAPE, ParticleTypes.GLOW};
                float[][] velDividers = {{1, 1, 1}, {1, 1, 1}};
                PacketsClient.roundParticleOutburst(client.world, 1000, particlesRound, target.getX(), target.getY(), target.getZ(), 1f);
                PacketsClient.particleOutburst(client.world, 500, particlesOut, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.UMBRAL_TRESPASS_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            double eyeHeight = buf.readDouble();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.SOUL_FIRE_FLAME};
                float[][] velDividers = {
                        {2, 2, 2}, {2, 2, 2}
                };
                PacketsClient.particleOutburst(client.world, 100, particles, target.getX(), eyeHeight, target.getZ(), velDividers);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.MOONFALL_ID, (client, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            int points = buf.readInt();
            client.execute(() -> {
                ParticleEffect[] particles = {ParticleTypes.LARGE_SMOKE, ParticleTypes.SOUL_FIRE_FLAME, ParticleRegistry.NIGHTFALL_PARTICLE, new ItemStackParticleEffect(ParticleTypes.ITEM, Items.DIRT.getDefaultStack()), new ItemStackParticleEffect(ParticleTypes.ITEM, Items.STONE.getDefaultStack())};
                float[][] velDividers = {{1, 8, 1}, {2, 8, 2}, {2, 8, 2}, {1, 2, 1}, {1, 2, 1}};
                PacketsClient.particleOutburst(client.world, points, particles, target.getX(), target.getY(), target.getZ(), velDividers);
            });
        });
    }

    private static double getParticleX(double widthScale, BlockPos pos, double width) {
        Random random = new Random();
        return pos.getX() + width * (2.0 * random.nextDouble() - 1.0) * widthScale;
    }

    private static double getParticleY(BlockPos pos, float height) {
        Random random = new Random();
        return pos.getY() + height * random.nextDouble();
    }

    private static double getParticleZ(double widthScale, BlockPos pos, double width) {
        Random random = new Random();
        return pos.getZ() + width * (2.0 * random.nextDouble() - 1.0) * widthScale;
    }

    public static void particleOutburst(World world, int particleNumber, ParticleEffect[] particles, double x, double y, double z, float[][] velDividers) {
        Random random = new Random();
        double d = random.nextGaussian() * 0.05D;
        double e = random.nextGaussian() * 0.05D;
        for(int j = 0; j < particleNumber; ++j) {
            double newX = random.nextDouble() - 1D * 0.5D + random.nextGaussian() * 0.15D + d;
            double newZ = random.nextDouble() - 1D * 0.5D + random.nextGaussian() * 0.15D + e;
            double newY = random.nextDouble() - 1D * 0.5D + random.nextDouble() * 0.5D;
            for (int i = 0; i < particles.length; i++) {
                world.addParticle(particles[i], x, y, z, newX/velDividers[i][0], newY/velDividers[i][1], newZ/velDividers[i][2]);
            }
        }
    }

    public static void particleOutburst(World world, int particleNumber, ParticleEffect[] particles, double x, double y, double z, float[][] velDividers, double sizeModifier) {
        Random random = new Random();
        double d = random.nextGaussian() * 0.05D;
        double e = random.nextGaussian() * 0.05D;
        for(int j = 0; j < particleNumber; ++j) {
            double newX = (random.nextDouble() - 1D * 0.5D + random.nextGaussian() * 0.15D + d)*sizeModifier;
            double newZ = (random.nextDouble() - 1D * 0.5D + random.nextGaussian() * 0.15D + e)*sizeModifier;
            double newY = random.nextDouble() - 1D * 0.5D + random.nextDouble() * 0.5D;
            for (int i = 0; i < particles.length; i++) {
                world.addParticle(particles[i], x, y, z, newX/velDividers[i][0], newY/velDividers[i][1], newZ/velDividers[i][2]);
            }
        }
    }

    public static void roundParticleOutburst(World world, double points, ParticleEffect[] particles, double x, double y, double z, float sizeModifier) {
        double phi = Math.PI * (3. - Math.sqrt(5.));
        for (int i = 0; i < points; i++) {
            double velocityY = 1 - (i/(points - 1)) * 2;
            double radius = Math.sqrt(1 - velocityY*velocityY);
            double theta = phi * i;
            double velocityX = Math.cos(theta) * radius;
            double velocityZ = Math.sin(theta) * radius;
            for (int j = 0; j < particles.length; j++) {
                world.addParticle(particles[j], true, x, y, z, velocityX*sizeModifier, velocityY*sizeModifier, velocityZ*sizeModifier);
            }
        } 
    }

    protected static DefaultParticleType[] randomParticle() {
        Random number = new Random();
        DefaultParticleType[][] particles = {
            {ParticleTypes.GLOW},
            {ParticleTypes.SMOKE},
            {ParticleTypes.EFFECT},
            {ParticleTypes.ENCHANTED_HIT},
            {ParticleTypes.PORTAL},
            {ParticleTypes.FLAME},
            {ParticleTypes.SOUL_FIRE_FLAME}
        };
        int rng = number.nextInt(particles.length);
        return particles[rng];
    }
}
