package at.petrak.paucal.fabric;

import net.fabricmc.api.ClientModInitializer;

public class FabricPaucalClientInit implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    FabricNetworkHandler.initClient();
  }
}
