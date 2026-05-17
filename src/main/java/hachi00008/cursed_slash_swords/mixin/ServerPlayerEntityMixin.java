package hachi00008.cursed_slash_swords.mixin;

import hachi00008.cursed_slash_swords.network.SoulSyncPayLoad;
import hachi00008.cursed_slash_swords.util.SoulDataAccessor;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin extends Player implements SoulDataAccessor {
    @Unique
    private int souls_count = 0;
    @Unique
    private int souls_max = 1000;

    public ServerPlayerEntityMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void injectWriteCustomData(ValueOutput output, CallbackInfo ci) {
        output.store("SoulCount", Codec.INT, this.souls_count);
        output.store("MaxSoulsPressure", Codec.INT, this.souls_max);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void injectReadCustomData(final ValueInput input, CallbackInfo ci) {
        this.souls_count = input.getIntOr("SoulCount", 0);
        this.souls_max = input.getIntOr("MaxSoulsPressure", 1000);
    }

    @Inject(method = "onUpdateAbilities", at = @At("TAIL"))
    private void syncOnLogin(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        ServerPlayNetworking.send(player, new SoulSyncPayLoad(this.souls_count, this.souls_max));
    }

    @Override public int getSoulCount() { return this.souls_count; }
    @Override public void setSoulCount(int amount) { this.souls_count = amount; }
    @Override public int getMaxSoul() { return this.souls_max; }
    @Override public void setMaxSoul(int amount) { this.souls_max = amount; }
}
