package hachi00008.cursed_slash_swords.item.custom;

import hachi00008.cursed_slash_swords.item.ModItems;
import hachi00008.cursed_slash_swords.network.SoulSyncPayLoad;
import hachi00008.cursed_slash_swords.util.SoulDataAccessor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpiritBottleItem extends Item {
    public SpiritBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (player instanceof ServerPlayer serverPlayer) {
            SoulDataAccessor accessor = (SoulDataAccessor) serverPlayer;

            accessor.setSoulCount(accessor.getSoulCount() + 100);
            ServerPlayNetworking.send(serverPlayer, new SoulSyncPayLoad(accessor.getSoulCount(), accessor.getMaxSoul()));

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.5F);
        }
        ItemStack resultStack = new ItemStack(ModItems.EMPTY_SPIRIT_BOTTLE);

        if (!player.getAbilities().instabuild) {
            if (heldStack.getCount() == 1) {
                player.setItemInHand(hand, resultStack);
                return InteractionResult.SUCCESS;
            } else {
                heldStack.shrink(1);
                if (!player.getInventory().add(resultStack)) {
                    player.drop(resultStack, false);
                }
            }
        } else {
            if (!player.getInventory().add(resultStack)) {
                player.drop(resultStack, false);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
