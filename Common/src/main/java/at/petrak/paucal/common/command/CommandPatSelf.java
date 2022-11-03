package at.petrak.paucal.common.command;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.common.misc.PatPat;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class CommandPatSelf {
    public static void add(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("pat")
            .then(Commands.argument("pattee", GameProfileArgument.gameProfile()).executes(ctx -> {
                var pattees = GameProfileArgument.getGameProfiles(ctx, "pattee");
                if (pattees.size() != 1) {
                    ctx.getSource().sendFailure(
                        Component.translatable("command.paucal.patSelf.bad_count", pattees.size()));
                    return 0;
                }
                return execute(pattees.iterator().next().getId(), ctx);
            }))
            .executes(ctx -> execute(ctx.getSource().getPlayerOrException().getUUID(), ctx)));
    }

    private static int execute(UUID target, CommandContext<CommandSourceStack> ctx) {
        var enabled = PaucalConfig.common().allowPats();
        if (!enabled) {
            ctx.getSource().sendFailure(Component.translatable("command.paucal.patSelf.disabled"));
            return 0;
        }

        PatPat.tryPlayPatSound(target, ctx.getSource().getPosition(), null, ctx.getSource().getLevel());
        return 1;
    }
}
