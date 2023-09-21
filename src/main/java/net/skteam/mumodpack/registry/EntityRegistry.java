package net.skteam.mumodpack.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.skteam.mumodpack.entity.custom.ChampBEntity;
import net.skteam.mumodpack.mumodpack;
import software.bernie.example.registry.EntityRegistryBuilder;

public class EntityRegistry {

    private static final String MOD_ID = mumodpack.MOD_ID;
    public static final ItemGroup MAIN_GROUP = mumodpack.MAIN_GROUP;

    public static final EntityType<ChampBEntity> CHAMP_B_ENTITY = EntityRegistryBuilder.<ChampBEntity>createBuilder(new Identifier(MOD_ID, "champ_b_entity")).category(SpawnGroup.MONSTER).entity(ChampBEntity::new).dimensions(EntityDimensions.changing(1F, 2F)).build();

    public static final Item CHAMP_B_ENTITY_SPAWN_EGG = new SpawnEggItem(CHAMP_B_ENTITY, 0, 10027008, new Item.Settings().group(MAIN_GROUP));

    public static void init() {
        FabricDefaultAttributeRegistry.register(CHAMP_B_ENTITY, ChampBEntity.createBanditAttributes());

        ItemRegistry.registerItem(CHAMP_B_ENTITY_SPAWN_EGG, "champb_spawn_egg");
    }
}
