package net.skteam.mumodpack.registry;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.skteam.mumodpack.events.BanditRade;

public class EventRegistry {
    public static void init() {
        AttackEntityCallback.EVENT.register(new BanditRade());
    }
}
