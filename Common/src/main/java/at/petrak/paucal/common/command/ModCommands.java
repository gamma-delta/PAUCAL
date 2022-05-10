package at.petrak.paucal.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dp) {
        CommandGetContributorInfo.register(dp);
    }
}
