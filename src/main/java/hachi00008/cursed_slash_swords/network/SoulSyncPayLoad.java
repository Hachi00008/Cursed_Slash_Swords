package hachi00008.cursed_slash_swords.network;

import hachi00008.cursed_slash_swords.CursedSlashSwords;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SoulSyncPayLoad(int count, int max) implements CustomPacketPayload {
    public static final Identifier SOUL_SYNC_ID = Identifier.fromNamespaceAndPath(CursedSlashSwords.MOD_ID, "soul_sync");
    public static final CustomPacketPayload.Type<SoulSyncPayLoad> TYPE = new Type<>(SOUL_SYNC_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SoulSyncPayLoad> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SoulSyncPayLoad::count,
            ByteBufCodecs.VAR_INT, SoulSyncPayLoad::max,
            SoulSyncPayLoad::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
