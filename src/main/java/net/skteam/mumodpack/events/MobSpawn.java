package net.skteam.mumodpack.events;

import net.mcreator.mineuniverseextra.entity.BanditScoutEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import net.skteam.mumodpack.registry.EntityRegistry;

import java.util.Random;

import static net.mcreator.mineuniverseextra.init.MineuniverseExtraModEntities.BANDIT_SCOUT;

public class MobSpawn {

    private static final Random random = new Random();

    private static double MX = 0;
    private static double MZ = 0;

    protected static double setMX(double MXL){
        return MX = MXL;
    }

    protected static double setMZ(double MZL){
        return MZ = MZL;
    }



    public static PillagerEntity getPillager(World world){
        PillagerEntity pillager = new PillagerEntity(EntityType.PILLAGER, world);
        return pillager;
    }

    public static VindicatorEntity getVindicator(World world){
        VindicatorEntity vindicator = new VindicatorEntity(EntityType.VINDICATOR, world);
        return vindicator;
    }

    public static RavagerEntity getRavager(World world){
        RavagerEntity ravager = new RavagerEntity(EntityType.RAVAGER, world);
        return ravager;
    }

    public static WitchEntity getWitch(World world){
        WitchEntity witch = new WitchEntity(EntityType.WITCH, world);
        return witch;
    }

    public static EvokerEntity getEvoker(World world){
        EvokerEntity evoker = new EvokerEntity(EntityType.EVOKER, world);
        return evoker;
    }

    public static ChampBEntity getBoss(World world){
        ChampBEntity boss = new ChampBEntity(EntityRegistry.CHAMP_B_ENTITY, world);
        return  boss;
    }

    public static BanditScoutEntity getScout(World world){
        BanditScoutEntity scout = new BanditScoutEntity(BANDIT_SCOUT, world);
        return scout;
    }

    public static void spawnBoss(World world, PlayerEntity player, int count){
        for(int i = 1; i < count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            ChampBEntity champB = getBoss(world);
            champB.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                champB.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(champB);
            }
        }
    }

    public static void spawnPillager(World world, PlayerEntity player, int count){
        for(int i = 1; i < count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            PillagerEntity pillager = getPillager(world);
            pillager.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                pillager.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(pillager);
            }
        }
    }

    public static void spawnVindicator(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            VindicatorEntity vindicator = getVindicator(world);
            vindicator.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                vindicator.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(vindicator);
            }
        }
    }

    public static void spawnRavager(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            RavagerEntity ravager = getRavager(world);
            ravager.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                ravager.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(ravager);
            }
        }
    }

    public static void spawnWitch(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            WitchEntity witch = getWitch(world);
            witch.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                witch.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(witch);
            }
        }
    }

    public static void spawnEvoker(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            EvokerEntity evoker = getEvoker(world);
            evoker.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                evoker.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(evoker);
            }
        }
    }

    public static void spawnScout(World world, PlayerEntity player, int count){
        for(int i = 1; i < count + 1; i++) {
            final double lX = random.nextDouble(25 + 15) - 15;
            final double lZ = random.nextDouble(25 + 15) - 15;
            final double lY = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int)(MX + lX), (int)(MZ + lZ));
            BanditScoutEntity scout = getScout(world);
            scout.setPosition(MX + lX, lY + 0.5, MZ + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(MX + lX, lY + 0.5, MZ + lZ);
                scout.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(scout);
            }
        }
    }

}
