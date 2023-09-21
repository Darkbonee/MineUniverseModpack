package net.skteam.mumodpack.registry;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.skteam.mumodpack.events.BanditRade;

public class EventRegistry {
    public static void init() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(new BanditRade());
    }
}
