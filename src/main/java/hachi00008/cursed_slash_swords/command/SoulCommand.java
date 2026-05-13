package hachi00008.cursed_slash_swords.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import hachi00008.cursed_slash_swords.network.SoulSyncPayLoad;
import hachi00008.cursed_slash_swords.util.SoulDataAccessor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class SoulCommand {

    public static void register(final CommandDispatcher<CommandSourceStack> dispatcher, final CommandBuildContext context) {
        dispatcher.register(
            Commands.literal("souls")
                .requires(Commands.hasPermission(Commands.LEVEL_MODERATORS))
                 .then(
                    Commands.literal("add")
                       .then(Commands.argument("targets", EntityArgument.players())
                           .then(Commands.argument("amount", IntegerArgumentType.integer())
                               .then(Commands.literal("count").executes(c -> execute(c, "add", "count")))
                               .then(Commands.literal("max").executes(c -> execute(c, "add", "max")))))
                        )
                .then(
                    Commands.literal("set")
                        .then(Commands.argument("targets", EntityArgument.players())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .then(Commands.literal("count").executes(c -> execute(c, "set", "count")))
                                .then(Commands.literal("max").executes(c -> execute(c, "set", "max")))))
                )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context, String action, String type) throws CommandSyntaxException {
        Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
        int amount = IntegerArgumentType.getInteger(context, "amount");

        for (ServerPlayer player : targets) {
            SoulDataAccessor accessor = (SoulDataAccessor) player;

            if (type.equals("count")) {
                int current = accessor.getSoulCount();
                int newValue = action.equals("add") ? current + amount : amount;
                accessor.setSoulCount(Math.max(0, newValue));
            } else {
                int current = accessor.getMaxSoul();
                int newValue = action.equals("add") ? current + amount : amount;
                accessor.setMaxSoul(Math.max(0, newValue));
            }

            ServerPlayNetworking.send(player, new SoulSyncPayLoad(accessor.getSoulCount(), accessor.getMaxSoul()));

        }

        return targets.size();
    }
}
