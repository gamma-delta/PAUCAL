package at.petrak.paucal;

import at.petrak.paucal.common.msg.FabricNetworkHandler;
import net.fabricmc.api.ClientModInitializer;

public class FabricPaucalClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricNetworkHandler.initClient();
    }
}
