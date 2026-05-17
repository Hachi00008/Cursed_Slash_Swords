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

public class EmptySpiritBottleItem extends Item {
    public EmptySpiritBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (player instanceof ServerPlayer serverPlayer) {
            SoulDataAccessor accessor = (SoulDataAccessor) serverPlayer;

            if (accessor.getSoulCount() < 100) {
                return InteractionResult.PASS;
            }

            accessor.setSoulCount(accessor.getSoulCount() - 100);
            ServerPlayNetworking.send(serverPlayer, new SoulSyncPayLoad(accessor.getSoulCount(), accessor.getMaxSoul()));

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ELDER_GUARDIAN_HURT, SoundSource.PLAYERS, 1.0F, 0.5F);

            ItemStack resultStack = new ItemStack(ModItems.SPIRIT_BOTTLE);

            if (!player.getAbilities().instabuild) {
                if (heldStack.getCount() == 1) {
                    player.setItemInHand(hand, resultStack);
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

        return InteractionResult.CONSUME;
    }
}