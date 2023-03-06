package at.petrak.paucal.common.msg;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.msg.PaucalMessage;
import at.petrak.paucal.common.ContributorsManifest;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static at.petrak.paucal.api.PaucalAPI.modLoc;

// The command is only run on the server of course so we need to the clients to know too
public record MsgReloadContributorsS2C() implements PaucalMessage {
    public static final ResourceLocation ID = modLoc("rlpat");

    @Override
    public void serialize(FriendlyByteBuf buf) {

    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    public static MsgReloadContributorsS2C deserialize(FriendlyByteBuf buf) {
        return new MsgReloadContributorsS2C();
    }

    public static void handle(MsgReloadContributorsS2C self) {
        Minecraft.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                PaucalAPI.LOGGER.info("Ordered by server to reload contributors");
                ContributorsManifest.loadContributors();
            }
        });
    }
}
