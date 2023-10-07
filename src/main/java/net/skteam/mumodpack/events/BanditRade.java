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
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BanditRade implements  AfterKilledOtherEntity {

    private int deadCounter = 0;

    private int wave = 0;

    private int total = 0;

    ServerBossBar raidBar = new ServerBossBar(Text.literal("Нашествие бандитов - волна 1"), BossBar.Color.RED, BossBar.Style.NOTCHED_12);

    //Создание волн

    protected void startScoutWave(PlayerEntity player, World world){

    }

    protected void startFirstWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 18);
        MobSpawn.spawnScout(world, player, 5);
    }

    protected  void startSecondWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 18);
        MobSpawn.spawnVindicator(world, player, 7);
    }

    protected void startThirdWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 15);
        MobSpawn.spawnVindicator(world, player, 6);
        MobSpawn.spawnRavager(world, player, 2);
        MobSpawn.spawnWitch(world, player, 3);
    }

    protected  void startFourthWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 18);
        MobSpawn.spawnVindicator(world, player, 7);
        MobSpawn.spawnWitch(world, player, 6);
    }

    protected  void startFifthWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 18);
        MobSpawn.spawnVindicator(world, player, 7);
        MobSpawn.spawnWitch(world, player, 2);
        MobSpawn.spawnEvoker(world, player, 3);
    }

    protected  void startSixthWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 18);
        MobSpawn.spawnVindicator(world, player, 6);
        MobSpawn.spawnWitch(world, player, 5);
        MobSpawn.spawnEvoker(world, player, 3);
    }

    protected void startSeventhWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 16);
        MobSpawn.spawnVindicator(world, player, 10);
        MobSpawn.spawnWitch(world, player, 4);
        MobSpawn.spawnEvoker(world, player, 4);
    }

    protected void startEighthWave(PlayerEntity player, World world){
        MobSpawn.spawnPillager(world, player, 16);
        MobSpawn.spawnVindicator(world, player, 10);
        MobSpawn.spawnWitch(world, player, 6);
        MobSpawn.spawnEvoker(world, player, 4);
        MobSpawn.spawnBoss(world, player, 1);
    }

    protected void endOfTheRaid(@NotNull PlayerEntity player){
        player.sendMessage(Text.literal("Остатки разбойников спасаются бегством! Мы победили!"));
    }


    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity , LivingEntity killedEntity) {

        if (entity instanceof PlayerEntity && killedEntity instanceof SheepEntity && wave == 0) {
            entity.sendMessage(Text.literal("Иллюзия развеялась... Легионы призваны!"));
            entity.sendMessage(Text.literal(entity.getName().getString() + " призвал нашествие бандитов!"));
            startFirstWave(((PlayerEntity) entity), world);
            raidBar.setName(Text.literal("Нашествие бандитов - волна 1"));
            raidBar.setPercent(1.0F);
            updateBarToPlayers(world);
            deadCounter = 0;
            wave = 1;
            total = 18;
        }

        if(killedEntity instanceof PillagerEntity || killedEntity instanceof VindicatorEntity ||
                killedEntity instanceof RavagerEntity || killedEntity instanceof WitchEntity || killedEntity instanceof ChampBEntity) {

            deadCounter = deadCounter + 1;

            updateBar((float) deadCounter, (float) total);
            updateBarToPlayers(world);

            if (deadCounter > 12 && wave == 1) {
                if(deadCounter == 13){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 5)"));
                }

                if(deadCounter == 14){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 4)"));
                }

                if(deadCounter == 15){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 3"));
                }

                if(deadCounter == 16){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 2)"));
                }

                if(deadCounter == 17){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 1 (врагов: 1"));
                }

            }

            if (deadCounter == 3 && wave == 1) {
                startSecondWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 2"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 2;
                total = 25;
            }

            if (deadCounter > 19 && wave == 2) {
                if(deadCounter == 20){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 5)"));
                }

                if(deadCounter == 21){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 4)"));
                }

                if(deadCounter == 22){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 3"));
                }

                if(deadCounter == 23){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 2)"));
                }

                if(deadCounter == 24){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 2 (врагов: 1"));
                }

            }

            if (deadCounter == 25 && wave == 2){
                startThirdWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 3"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 3;
                total = 26;
            }

            if (deadCounter > 20 && wave == 3) {
                if(deadCounter == 21){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 5)"));
                }

                if(deadCounter == 22){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 4)"));
                }

                if(deadCounter == 23){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 3"));
                }

                if(deadCounter == 24){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 2)"));
                }

                if(deadCounter == 25){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 3 (врагов: 1"));
                }

            }

            if (deadCounter == 26 && wave == 3){
                startFourthWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 4"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 4;
                total = 31;
            }

            if (deadCounter > 25 && wave == 4) {
                if(deadCounter == 26){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 5)"));
                }

                if(deadCounter == 27){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 4)"));
                }

                if(deadCounter == 28){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 3"));
                }

                if(deadCounter == 29){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 2)"));
                }

                if(deadCounter == 30){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 4 (врагов: 1"));
                }

            }

            if(deadCounter == 31 && wave == 4){
                startFifthWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 5"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 5;
                total = 30;
            }

            if (deadCounter > 24 && wave == 5) {
                if(deadCounter == 25){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 5)"));
                }

                if(deadCounter == 26){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 4)"));
                }

                if(deadCounter == 27){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 3"));
                }

                if(deadCounter == 28){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 2)"));
                }

                if(deadCounter == 29){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 5 (врагов: 1"));
                }

            }

            if(deadCounter == 30 && wave == 5){
                startSixthWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 6"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 6;
                total = 32;
            }

            if (deadCounter > 26 && wave == 6) {
                if(deadCounter == 27){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 5)"));
                }

                if(deadCounter == 28){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 4)"));
                }

                if(deadCounter == 29){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 3"));
                }

                if(deadCounter == 30){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 2)"));
                }

                if(deadCounter == 31){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 6 (врагов: 1"));
                }

            }

            if(deadCounter == 32 && wave == 6){
                startSeventhWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 7"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 7;
                total = 34;
            }

            if (deadCounter > 28 && wave == 7) {
                if(deadCounter == 29){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 5)"));
                }

                if(deadCounter == 30){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 4)"));
                }

                if(deadCounter == 31){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 3"));
                }

                if(deadCounter == 32){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 2)"));
                }

                if(deadCounter == 33){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 7 (врагов: 1"));
                }

            }

            if(deadCounter == 34 && wave == 7){
                startEighthWave(((PlayerEntity) entity), world);
                raidBar.setName(Text.literal("Нашествие бандитов - волна 8"));
                raidBar.setPercent(1.0F);
                deadCounter = 0;
                wave = 8;
                total = 37;
            }

            if (deadCounter > 31 && wave == 8) {
                if(deadCounter == 32){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 5)"));
                }

                if(deadCounter == 33){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 4)"));
                }

                if(deadCounter == 34){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 3"));
                }

                if(deadCounter == 35){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 2)"));
                }

                if(deadCounter == 36){
                    raidBar.setName(Text.literal("Нашествие бандитов - волна 8 (врагов: 1"));
                }

            }

            if(deadCounter == 37 && wave == 8){
                endOfTheRaid(((PlayerEntity) entity));
                deadCounter = 0;
                wave = 0;
                raidBar.setVisible(false);
            }
        }
    }

    //Bossbar
    public void updateBar(float deadCounter, float total){
        raidBar.setPercent((1.0F - (deadCounter/total)));
    }

    private void updateBarToPlayers(@NotNull ServerWorld world){
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
            }
        }
    }

    protected int getNumberOfPlayers(@NotNull ServerWorld world){
        int count = 0;
        List<ServerPlayerEntity> worldPlayerList = world.getPlayers();
        List<ServerPlayerEntity> raidPlayerlist = (List<ServerPlayerEntity>) raidBar.getPlayers();
        Iterator worldPlayerIterator = worldPlayerList.iterator();
        Iterator raidPlayerIterator = raidPlayerlist.iterator();

        String[] nameInBar = new String[raidBar.getPlayers().size()];
        for (int i = 0; i < nameInBar.length; i++) {
            if(raidPlayerIterator.hasNext()){
                nameInBar[i] = raidPlayerlist.get(i).toString();
                raidPlayerIterator.next();
            }
        }


        ServerPlayerEntity serverPlayerEntity;
        while(worldPlayerIterator.hasNext()) {
            serverPlayerEntity = (ServerPlayerEntity)worldPlayerIterator.next();
            String name = serverPlayerEntity.toString();

            for (int i = 0; i < nameInBar.length; i++) {
                if(nameInBar[i].equals(name)){
                    count++;
                }
            }
        }
        return count;
    }

}
