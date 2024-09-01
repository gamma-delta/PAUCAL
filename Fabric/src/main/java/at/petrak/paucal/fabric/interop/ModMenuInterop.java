package at.petrak.paucal.fabric.interop;

import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuInterop implements ModMenuApi {
  // No config here anymore
}
