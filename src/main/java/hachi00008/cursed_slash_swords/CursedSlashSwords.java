package hachi00008.cursed_slash_swords;

import hachi00008.cursed_slash_swords.block.ModBlocks;
import hachi00008.cursed_slash_swords.command.SoulCommand;
import hachi00008.cursed_slash_swords.creativemodetab.ModCreativeModeTabs;
import hachi00008.cursed_slash_swords.item.ModItems;
import hachi00008.cursed_slash_swords.network.SoulSyncPayLoad;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursedSlashSwords implements ModInitializer {
	public static final String MOD_ID = "cursed_slash_swords";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCreativeModeTabs.registerCreativeModeTabs();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		PayloadTypeRegistry.clientboundPlay().register(SoulSyncPayLoad.TYPE, SoulSyncPayLoad.CODEC);

		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> {
			SoulCommand.register(dispatcher, buildContext);
		});
	}
}