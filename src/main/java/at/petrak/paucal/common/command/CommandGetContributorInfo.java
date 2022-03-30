package at.petrak.paucal.common.command;

import at.petrak.paucal.api.contrib.Contributors;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class CommandGetContributorInfo {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("paucal:contributor")
            .then(Commands.argument("target", EntityArgument.player())
                .then(Commands.literal("getAll")
                    .executes(ctx -> info(ctx, EntityArgument.getPlayer(ctx, "target"), true)))
                .executes(ctx -> info(ctx, EntityArgument.getPlayer(ctx, "target"), false))));
    }

    private static int info(CommandContext<CommandSourceStack> ctx, ServerPlayer target,
        boolean allKVs) {
        var contrib = Contributors.getContributor(target.getUUID());
        if (contrib == null) {
            ctx.getSource()
                .sendFailure(
                    new TranslatableComponent("command.paucal:contributor.not_contributor", target.getDisplayName()));
            return 0;
        }
        var type = contrib.getContributorType();
        var keySet = contrib.allKeys();

        var out = new TranslatableComponent("command.paucal:contributor",
            target.getDisplayName(), type.level(), type.isDev(), type.isCool(), keySet.size());
        if (allKVs) {
            var keys = keySet.stream().sorted().toList();
            for (var key : keys) {
                out.append("\n");
                out.append(new TextComponent(key).withStyle(ChatFormatting.GOLD));
                out.append(new TextComponent(": "));
                out.append(new TextComponent(contrib.getRaw(key).toString()).withStyle(ChatFormatting.LIGHT_PURPLE));
            }
        }
        ctx.getSource().sendSuccess(out, true);
        return keySet.size();
    }
}
