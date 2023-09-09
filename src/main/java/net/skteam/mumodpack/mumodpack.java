package net.skteam.mumodpack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.skteam.mumodpack.config.ConfigConstructor;
import net.skteam.mumodpack.config.MidnightConfig;
import net.skteam.mumodpack.registry.EntityRegistry;
import net.skteam.mumodpack.registry.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

// Very important comment
public class mumodpack implements ModInitializer {
	public static final String MOD_ID = "mumodpack";

	public static final ItemGroup MAIN_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "general"), () -> new ItemStack(ItemRegistry.BOSS_AXE.asItem()));

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, ConfigConstructor.class);
		LOGGER.info("Config initialized!");
		ItemRegistry.init();
		EntityRegistry.init();
		LOGGER.info("Successfully registered MineUniverse content!");
		GeckoLib.initialize();
		LOGGER.info("Successfully initialized Geckolib!");
		LOGGER.info("Initializing done!");
	}
}