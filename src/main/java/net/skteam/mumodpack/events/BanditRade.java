package net.skteam.mumodpack.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents.AfterKilledOtherEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import net.skteam.mumodpack.registry.EntityRegistry;

import java.util.Random;

public class BanditRade implements  AfterKilledOtherEntity {

    private int deadCounter = 0;

    private int wave = 1;

    private final Random random = new Random();


    //Создание волн
    protected void startFirstWave(PlayerEntity player, World world){
        spawnPillager(world, player, 18);
    }

    protected  void startSecondWave(PlayerEntity player, World world){
        player.sendMessage(Text.literal("Тест2")); //проверка на запуск метода (если выведет в чат, то работает)
        spawnPillager(world, player, 18);
        spawnVindicator(world, player, 7);

    }

    protected void startThirdWave(PlayerEntity player, World world){
        spawnPillager(world, player, 15);
        spawnVindicator(world, player, 6);
        spawnRavager(world, player, 2);
        spawnWitch(world, player, 3);
    }

    protected  void startFourthWave(PlayerEntity player, World world){
        spawnPillager(world, player, 18);
        spawnVindicator(world, player, 7);
        spawnWitch(world, player, 6);
    }

    protected  void startFifthWave(PlayerEntity player, World world){
        spawnPillager(world, player, 18);
        spawnVindicator(world, player, 7);
        spawnWitch(world, player, 2);
    }

    protected  void startSixthWave(PlayerEntity player, World world){
        spawnPillager(world, player, 18);
        spawnVindicator(world, player, 6);
        spawnWitch(world, player, 5);
    }

    protected void startSeventhWave(PlayerEntity player, World world){
        spawnPillager(world, player, 16);
        spawnVindicator(world, player, 10);
        spawnWitch(world, player, 4);
    }

    protected void startEighthWave(PlayerEntity player, World world){
        spawnPillager(world, player, 16);
        spawnVindicator(world, player, 10);
        spawnWitch(world, player, 6);
        spawnBoss(world, player, 1);
    }

    protected void endOfTheRaid(PlayerEntity player){
        player.sendMessage(Text.literal("Разбойники отступают! Мы победили!"));
    }

    public PillagerEntity getPillager(World world){
        PillagerEntity pillager = new PillagerEntity(EntityType.PILLAGER, world);
        return pillager;
    }

    public VindicatorEntity getVindicator(World world){
       VindicatorEntity vindicator = new VindicatorEntity(EntityType.VINDICATOR, world);
        return vindicator;
    }

    public RavagerEntity getRavager(World world){
       RavagerEntity ravager = new RavagerEntity(EntityType.RAVAGER, world);
        return ravager;
    }

    public WitchEntity getWitch(World world){
        WitchEntity witch = new WitchEntity(EntityType.WITCH, world);
        return witch;
    }

    public ChampBEntity getBoss(World world){
        ChampBEntity boss = new ChampBEntity(EntityRegistry.CHAMP_B_ENTITY, world);
        return  boss;
    }

    public void spawnBoss(World world, PlayerEntity player, int count){
        for(int i = 1; i < count + 1; i++) {
            ChampBEntity champB = getBoss(world);
            final double lX = random.nextDouble(15 + 10) - 10;
            final double lZ = random.nextDouble(15 + 10) - 10;
            champB.setPosition(player.getX() + lX, player.getY() + 0.5, player.getZ() + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(player.getX() - lX, player.getY() + 0.5, player.getZ() - lZ);
                champB.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(champB);
            }
        }
    }

    public void spawnPillager(World world, PlayerEntity player, int count){
        for(int i = 1; i < count + 1; i++) {
            PillagerEntity pillager = getPillager(world);
            final double lX = random.nextDouble(15 + 10) - 10;
            final double lZ = random.nextDouble(15 + 10) - 10;
            pillager.setPosition(player.getX() + lX, player.getY() + 0.5, player.getZ() + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(player.getX() - lX, player.getY() + 0.5, player.getZ() - lZ);
                pillager.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(pillager);
            }
        }
    }

    public void spawnVindicator(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            VindicatorEntity vindicator = getVindicator(world);
            final double lX = random.nextDouble(15 + 10) - 10;
            final double lZ = random.nextDouble(15 + 10) - 10;
            vindicator.setPosition(player.getX() + lX, player.getY() + 0.5, player.getZ() + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(player.getX() - lX, player.getY() + 0.5, player.getZ() - lZ);
                vindicator.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(vindicator);
            }
        }
    }

    public void spawnRavager(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            final double lX = random.nextDouble(15 + 10) - 10;
            final double lZ = random.nextDouble(15 + 10) - 10;
            RavagerEntity ravager = getRavager(world);
            ravager.setPosition(player.getX() + lX, player.getY() + 0.5, player.getZ() + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(player.getX() - lX, player.getY() + 0.5, player.getZ() - lZ);
                ravager.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(ravager);
            }
        }
    }

    public void spawnWitch(World world, PlayerEntity player, int count){
        for(int i = 1; i <count + 1; i++) {
            final double lX = random.nextDouble(15 + 10) - 10;
            final double lZ = random.nextDouble(15 + 10) - 10;
            WitchEntity witch = getWitch(world);
            witch.setPosition(player.getX() + lX, player.getY() + 0.5, player.getZ() + lZ);
            if (world instanceof ServerWorld) {
                BlockPos pos = new BlockPos(player.getX() - lX, player.getY() + 0.5, player.getZ() - lZ);
                witch.initialize((ServerWorld) world, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
                world.spawnEntity(witch);
            }
        }
    }

    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity , LivingEntity killedEntity) {

        if (killedEntity instanceof SheepEntity) {
            entity.sendMessage(Text.literal("Иллюзия развеялась... Легионы призваны!"));
            entity.sendMessage(Text.literal(entity.getName().getString() + " призвал нашествие бандитов!"));
            startFirstWave(((PlayerEntity) entity), world);
        }

        if(killedEntity instanceof PillagerEntity || killedEntity instanceof VindicatorEntity ||
                killedEntity instanceof RavagerEntity || killedEntity instanceof WitchEntity) {

            deadCounter = deadCounter + 1;

            if (deadCounter == 18 && wave == 1) {
                startSecondWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 2;
            }

            if (deadCounter == 25 && wave == 2){
                startThirdWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 3;
            }

            if (deadCounter == 26 && wave == 3){
                startFourthWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 4;
            }

            if(deadCounter == 31 && wave == 4){
                startFifthWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 5;
            }

            if(deadCounter == 27 && wave == 5){
                startSixthWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 6;
            }

            if(deadCounter == 29 && wave == 6){
                startSeventhWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 7;
            }

            if(deadCounter == 30 && wave == 7){
                startEighthWave(((PlayerEntity) entity), world);
                deadCounter = 0;
                wave = 8;
            }

            if(deadCounter == 33 && wave == 8){
                endOfTheRaid(((PlayerEntity) entity));
                deadCounter = 0;
                wave = 0;
            }

        }
    }

}
