package net.skteam.mumodpack.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.skteam.mumodpack.Item.bossAxe;
import net.skteam.mumodpack.mumodpack;

public class ItemRegistry {
  
    public static final ItemGroup MAIN_GROUP = mumodpack.MAIN_GROUP;
    public static final Item BOSS_AXE = new bossAxe(new FabricItemSettings().group(MAIN_GROUP).maxDamage(4000));

    public static void init() {
        registerItem(BOSS_AXE, "boss_axe");
    }


    public static <I extends Item> I registerItem(I item, String name) {
        return Registry.register(Registry.ITEM, new Identifier(mumodpack.MOD_ID, name), item);
    }

}
