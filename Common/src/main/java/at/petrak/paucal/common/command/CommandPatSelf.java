package at.petrak.paucal.common.command;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.common.misc.PatPat;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

public class CommandPatSelf {
    public static void add(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("patSelf")
            .executes(ctx -> {
                var enabled = PaucalConfig.common().allowPats();
                if (!enabled) {
                    ctx.getSource().sendFailure(new TranslatableComponent("command.paucal.patSelf.disabled"));
                    return 0;
                }

                ServerPlayer exeggutor = ctx.getSource().getPlayerOrException();
                PatPat.onPat(exeggutor, ctx.getSource().getLevel(), InteractionHand.MAIN_HAND, exeggutor, null);

                return 1;
            }));
    }
}
