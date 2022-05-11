package at.petrak.paucal.common.command;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.common.Contributors;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class CommandReloadContributors {
    public static void add(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("reload").requires(css -> css.hasPermission(Commands.LEVEL_ADMINS))
            .executes(ctx -> {
                var enabled = PaucalConfig.common().loadContributors();
                if (!enabled) {
                    ctx.getSource().sendFailure(new TranslatableComponent("command.paucal.reload.disabled"));
                    return 0;
                }

                Contributors.forceLoadContributors();

                ctx.getSource().sendSuccess(new TranslatableComponent("command.paucal.reload"), true);
                return 1;
            }));
    }
}
