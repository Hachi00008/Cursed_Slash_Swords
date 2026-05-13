package hachi00008.cursed_slash_swords;

import hachi00008.cursed_slash_swords.client.SoulClientData;
import hachi00008.cursed_slash_swords.network.SoulSyncPayLoad;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CursedSlashSwordsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SoulSyncPayLoad.TYPE,(payload, context) -> {
            context.client().execute(() -> {
                SoulClientData.currentSoul = payload.count();
                SoulClientData.maxSoulPressure = payload.max();
            });
        });
    }
}
