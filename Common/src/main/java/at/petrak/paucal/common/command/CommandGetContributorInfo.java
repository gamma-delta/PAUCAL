package at.petrak.paucal.common.command;

import at.petrak.paucal.common.ContributorsManifest;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CommandGetContributorInfo {
    public static void add(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("getInfo")
            .then(Commands.argument("target", EntityArgument.player())
                .executes(ctx -> info(ctx, EntityArgument.getPlayer(ctx, "target"), false))
                .then(Commands.literal("getAll")
                    .executes(ctx -> info(ctx, EntityArgument.getPlayer(ctx, "target"), true)))
            ));
    }

    private static int info(CommandContext<CommandSourceStack> ctx, ServerPlayer target,
        boolean allKVs) {
        var contrib = ContributorsManifest.getContributor(target.getUUID());
        if (contrib == null) {
            ctx.getSource()
                .sendFailure(
                    Component.translatable("command.paucal.contributor.not_contributor", target.getDisplayName()));
            return 0;
        }
        var keySet = contrib.allKeys();

        var out = Component.translatable("command.paucal.contributor",
            target.getDisplayName(), contrib.getLevel(), contrib.getLevel(), keySet.size());
        if (allKVs) {
            var keys = keySet.stream().sorted().toList();
            for (var key : keys) {
                out.append("\n- ");
                out.append(Component.literal(key).withStyle(ChatFormatting.GOLD));
                out.append(Component.literal(": "));
                out.append(Component.literal(
                    String.valueOf(contrib.<Object>get(key))).withStyle(ChatFormatting.LIGHT_PURPLE));
            }
        }
        ctx.getSource().sendSuccess(out, true);
        return keySet.size();
    }
}
