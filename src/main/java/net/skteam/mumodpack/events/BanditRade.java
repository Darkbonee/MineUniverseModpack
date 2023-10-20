package net.skteam.mumodpack.events;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents.AfterKilledOtherEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.skteam.mumodpack.entity.custom.BscoutEntity;
import net.skteam.mumodpack.entity.custom.ChampBEntity;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BanditRade implements  AfterKilledOtherEntity {

    private int deadCounter = 0;
    private int wave = 0;
    private int total = 0;
    private int numberOfPlayersInRaid = 1;


    ServerBossBar raidBar = new ServerBossBar(Text.literal("Нашествие бандитов - волна 1"), BossBar.Color.RED, BossBar.Style.NOTCHED_12);


    //Создание волн


    protected void startFirstWave(ServerWorld world){
        double numberOfPillager = 18*getMultiple(numberOfPlayersInRaid);
        MobSpawn.spawnPillager(world, (int) numberOfPillager);

        this.total = (int) numberOfPillager;
    }

    protected  void startSecondWave(ServerWorld world){
        double numberOfPillager = 18*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 8*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);

        this.total = ((int) numberOfPillager) + ((int) numberOfVindicator);
    }

    protected void startThirdWave(ServerWorld world){
        double numberOfPillager = 16*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 6*getMultiple(numberOfPlayersInRaid);
        double numberOfRavager = 2*getMultiple(numberOfPlayersInRaid);
        double numberOfWitch = 4*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);
        MobSpawn.spawnRavager(world, (int) numberOfRavager);
        MobSpawn.spawnWitch(world, (int) numberOfWitch);

        this.total = ((int) numberOfPillager) + ((int) numberOfVindicator) + ((int) numberOfRavager) + ((int) numberOfWitch);
    }

    protected  void startFourthWave(ServerWorld world){
        double numberOfPillager = 18*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 8*getMultiple(numberOfPlayersInRaid);
        double numberOfWitch = 6*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);
        MobSpawn.spawnWitch(world, (int) numberOfWitch);

        this.total = ((int) numberOfPillager) + ((int) numberOfVindicator) + ((int) numberOfWitch);
    }

    protected  void startFifthWave(ServerWorld world){
        double numberOfPillager = 18*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 8*getMultiple(numberOfPlayersInRaid);
        double numberOfWitch = 2*getMultiple(numberOfPlayersInRaid);
        double numberOfEvoker = 4*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);
        MobSpawn.spawnWitch(world, (int) numberOfWitch);
        MobSpawn.spawnEvoker(world, (int) numberOfEvoker);

        this.total = ((int) numberOfPillager) + ((int) numberOfVindicator) + ((int) numberOfEvoker) + ((int) numberOfWitch);
    }

    protected  void startSixthWave(ServerWorld world){
        double numberOfPillager = 18*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 6*getMultiple(numberOfPlayersInRaid);
        double numberOfWitch = 6*getMultiple(numberOfPlayersInRaid);
        double numberOfEvoker = 4*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);
        MobSpawn.spawnWitch(world, (int) numberOfWitch);
        MobSpawn.spawnEvoker(world, (int) numberOfEvoker);

        this.total = ((int) numberOfPillager) + ((int) numberOfVindicator) + ((int) numberOfEvoker) + ((int) numberOfWitch);
    }

    protected void startSeventhWave(ServerWorld world){
        double numberOfPillager = 16*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 10*getMultiple(numberOfPlayersInRaid);
        double numberOfWitch = 4*getMultiple(numberOfPlayersInRaid);
        double numberOfEvoker = 4*getMultiple(numberOfPlayersInRaid);
        double numberOfRavager = 4*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);
        MobSpawn.spawnRavager(world, (int) numberOfRavager);
        MobSpawn.spawnWitch(world, (int) numberOfWitch);
        MobSpawn.spawnEvoker(world, (int) numberOfEvoker);

        this.total = ((int) numberOfPillager) + ((int) numberOfVindicator) + ((int) numberOfEvoker) + ((int) numberOfWitch)+ ((int) numberOfRavager);
    }

    protected void startEighthWave(ServerWorld world){
        double numberOfPillager = 16*getMultiple(numberOfPlayersInRaid);
        double numberOfVindicator = 10*getMultiple(numberOfPlayersInRaid);
        double numberOfWitch = 6*getMultiple(numberOfPlayersInRaid);
        double numberOfRavager = 2*getMultiple(numberOfPlayersInRaid);
        double numberOfEvoker = 4*getMultiple(numberOfPlayersInRaid);

        MobSpawn.spawnPillager(world, (int) numberOfPillager);
        MobSpawn.spawnVindicator(world, (int) numberOfVindicator);
        MobSpawn.spawnWitch(world, (int) numberOfWitch);
        MobSpawn.spawnRavager(world, (int) numberOfRavager);
        MobSpawn.spawnEvoker(world, (int) numberOfEvoker);
        MobSpawn.spawnBoss(world, 1);

        this.total = 1 + ((int) numberOfPillager) + ((int) numberOfVindicator) + ((int) numberOfEvoker) + ((int) numberOfWitch)+ ((int) numberOfRavager);
    }

    protected void endOfTheRaid(PlayerEntity player){
        player.sendMessage(Text.literal("Остатки разбойников спасаются бегством! Вы победили!"));
    }


    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity , LivingEntity killedEntity) {

        if (entity instanceof PlayerEntity && killedEntity instanceof BscoutEntity) {
            entity.sendMessage(Text.literal("Вы убили одного из разведчиков основных сил разбойников. Они что-то заподозрили..."));
            MobSpawn.setMX(entity.getX());
            MobSpawn.setMZ(entity.getZ());
            entity.sendMessage(Text.literal(entity.getName().getString() + " призвал нашествие бандитов!"));
            startFirstWave(world);
            raidBar.setName(Text.literal("Нашествие бандитов - волна 1"));
            raidBar.setPercent(1.0F);
            updateBarToPlayers(world, (PlayerEntity) entity);
            deadCounter = 0;
            wave = 1;
        }

        if((killedEntity instanceof PillagerEntity || killedEntity instanceof VindicatorEntity ||
                killedEntity instanceof RavagerEntity || killedEntity instanceof WitchEntity || killedEntity instanceof ChampBEntity)
                && !(killedEntity instanceof BscoutEntity)) {
            deadCounter = deadCounter + 1;

            updateBar((float) deadCounter, (float) total);
            updateBarToPlayers(world, (PlayerEntity) entity);


            if (deadCounter > (total - 6) && wave == 1) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 1)"));
                }

            }

            if ((deadCounter == total) && wave == 1) {
                startSecondWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 2"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 2;
            }

            if (deadCounter > (total - 6) && wave == 2) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 1)"));
                }

            }

            if ((deadCounter == total) && wave == 2){
                startThirdWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 3"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 3;
            }

            if (deadCounter > (total - 6) && wave == 3) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 1)"));
                }

            }

            if ((deadCounter == total) && wave == 3){
                startFourthWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 4"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 4;
            }

            if (deadCounter > (total - 6) && wave == 4) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 1)"));
                }

            }

            if((deadCounter == total) && wave == 4){
                startFifthWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 5"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 5;
            }

            if (deadCounter > (total - 6) && wave == 5) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 1)"));
                }

            }

            if((deadCounter == total) && wave == 5){
                startSixthWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 6"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 6;
            }

            if (deadCounter > (total - 6) && wave == 6) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 1)"));
                }

            }

            if((deadCounter == total) && wave == 6){
                startSeventhWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 7"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 7;
            }

            if (deadCounter > (total - 6) && wave == 7) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 1)"));
                }

            }

            if((deadCounter == total) && wave == 7){
                startEighthWave(world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 8"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 8;
            }

            if ((deadCounter > total) && wave == 8) {
                if(deadCounter == (total - 5)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 5)"));
                }

                if(deadCounter == (total - 4)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 4)"));
                }

                if(deadCounter == (total - 3)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 3)"));
                }

                if(deadCounter == (total - 2)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 2)"));
                }

                if(deadCounter == (total - 1)){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 1)"));
                }

            }

            if((deadCounter == total) && wave == 8){
                endOfTheRaid((PlayerEntity) entity);
                deadCounter = 0;
                wave = 0;
                raidBar.clearPlayers();
                raidBar.setVisible(false);
                updateBarToPlayers(world, (PlayerEntity) entity);
            }
        }
    }

    //Bossbar
    public void updateBar(float deadCounter, float total){
        raidBar.setPercent((1.0F - (deadCounter/total)));
    }

    private void updateBarToPlayers(ServerWorld world, PlayerEntity entity){
        numberOfPlayersInRaid = 0;
        Set<ServerPlayerEntity> set = Sets.newHashSet(raidBar.getPlayers());
        List<ServerPlayerEntity> list = world.getPlayers();
        Iterator var3 = list.iterator();

        ServerPlayerEntity serverPlayerEntity;
        while(var3.hasNext()) {
            serverPlayerEntity = (ServerPlayerEntity)var3.next();
            if (!set.contains(serverPlayerEntity)) {
                raidBar.addPlayer(serverPlayerEntity);
            }
        }

        var3 = set.iterator();

        while(var3.hasNext()) {
            serverPlayerEntity = (ServerPlayerEntity)var3.next();
            if (!list.contains(serverPlayerEntity)) {
                raidBar.removePlayer(serverPlayerEntity);
                numberOfPlayersInRaid = numberOfPlayersInRaid - 1;
                serverPlayerEntity.clearStatusEffects();
            }

            if(list.contains(serverPlayerEntity)){
                numberOfPlayersInRaid = numberOfPlayersInRaid + 1;
                entity.sendMessage(Text.literal(numberOfPlayersInRaid + ""));
            }
        }
    }

    private double getMultiple(int numberOfPlayersInRaid){
        double multiple = 1;
        if(numberOfPlayersInRaid <3){
            multiple = 0.5;
        }
        else if((numberOfPlayersInRaid >= 3) && (numberOfPlayersInRaid < 6)){
            multiple = 1;
        }
        else if((numberOfPlayersInRaid >= 6) && (numberOfPlayersInRaid < 9)){
            multiple = 1.5;
        }
        else {
            multiple = 2;
        }
        return  multiple;
    }


}
