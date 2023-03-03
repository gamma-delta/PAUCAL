package at.petrak.paucal.common.command;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.common.ContributorsManifest;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandReloadContributors {
    public static void add(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("reload").requires(css -> css.hasPermission(Commands.LEVEL_ADMINS))
            .executes(ctx -> {
                var enabled = PaucalConfig.common().loadContributors();
                if (!enabled) {
                    ctx.getSource().sendFailure(Component.translatable("command.paucal.reload.disabled"));
                    return 0;
                }

                ContributorsManifest.forceLoadContributors();

                ctx.getSource().sendSuccess(Component.translatable("command.paucal.reload"), true);
                return 1;
            }));
    }
}
