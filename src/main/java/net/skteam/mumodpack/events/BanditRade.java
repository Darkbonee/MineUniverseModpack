package net.skteam.mumodpack.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BanditRade implements AttackEntityCallback {

    private PlayerEntity player;

    private final Random random = new Random();


    //Создание волн
    protected void startFirstWave(PlayerEntity player, World world){
        int deadCounter = 0;
        player.sendMessage(Text.literal("Тест")); //проверка на запуск метода (если выведет в чат, то работает)
        spawnPillager(world, player, 18);
//        if(pillager.isDead()){
//            deadCounter += 1;
//        }
//        if(deadCounter == 1){
//            player.sendMessage(Text.literal("умер бандит"));
//        }

    }

    protected  void startSecondWave(PlayerEntity player, World world){
        player.sendMessage(Text.literal("Тест")); //проверка на запуск метода (если выведет в чат, то работает)
        spawnPillager(world, player, 18);

    }

    protected void startThirdWave(){

    }

    protected  void startFourthWave(){

    }

    protected  void startFifthWave(){

    }

    protected  void startSixthWave(){

    }

    protected void startSeventhWave(){

    }

    protected void startEighthWave(){

    }

    protected void endOfTheRaid(){

    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity,
                                 @Nullable EntityHitResult hitResult) {
        if(entity instanceof SheepEntity && !world.isClient){
            ((SheepEntity) entity).setHealth(0);
            player.sendMessage(Text.literal( "Иллюзия развеялась... Легионы призваны!"));
            player.sendMessage(Text.literal(player.getName().getString() + " призвал нашествие бандитов!"));
            for(int i = 1; i < 10; i++) {
                switch (i){
                    case 1: {
                        startFirstWave(player, world);
                        break;
                    }

                }

            }
        }
        return ActionResult.PASS;
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
            final double lX = random.nextDouble(15 + 10) - 10;
            final double lZ = random.nextDouble(15 + 10) - 10;
            VindicatorEntity vindicator = getVindicator(world);
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


}
