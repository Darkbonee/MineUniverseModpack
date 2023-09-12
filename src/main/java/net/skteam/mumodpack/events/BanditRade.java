package net.skteam.mumodpack.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BanditRade implements AttackEntityCallback {

    private PlayerEntity player;

    private VindicatorEntity vindicator;

    private final Random random = new Random();

    private final double lX = random.nextDouble(2) + 5;
    private final double lZ = random.nextDouble(2) + 5;


    //Создание волн
    protected void startFirstWave(World world, PlayerEntity player){

            PillagerEntity pillager = new PillagerEntity(EntityType.PILLAGER, world);
            player.sendMessage(Text.literal("Тест")); //проверка на запуск метода (если выведет в чат, то работает)
            pillager.setPosition(player.getPos());
            world.spawnEntity(pillager);

    }

    protected  void startSecondWave(){

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
            for(int i = 1; i < 10; i ++) {
                switch (i){
                    case 1: startFirstWave(world, player);

                }

            }
        }
        return ActionResult.PASS;
    }

}
