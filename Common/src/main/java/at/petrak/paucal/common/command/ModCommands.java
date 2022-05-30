package at.petrak.paucal.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dp) {
        var builder = Commands.literal("paucal");
        CommandGetContributorInfo.add(builder);
        CommandReloadContributors.add(builder);
        CommandPatSelf.add(builder);

        dp.register(builder);
    }
}
