package at.petrak.paucal.forge.mixin;

/*
This file is stolen, I mean graciously borrowed, from puzzleslib, licensed under Mozilla Public License Version 2.0
https://github.com/Fuzss/puzzleslib/blob/main/1.21/NeoForge/src/main/java/fuzs/puzzleslib/neoforge/mixin/DatagenModLoaderNeoForgeMixin.java

To fix this issue: https://github.com/architectury/architectury-loom/issues/189
*/

import at.petrak.paucal.xplat.PaucalMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DatagenModLoader.class, remap = false)
public class ArchiDatagenStoppinator {
  @WrapOperation(method = "begin", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/data/event/GatherDataEvent$DataGeneratorConfig;runAll()V"))
  private static void begin(GatherDataEvent.DataGeneratorConfig dataGeneratorConfig, Operation<Void> operation) {
    // architectury loom does not exit the data run configuration, this will allow it to do so
    if (!FMLEnvironment.production && isRunningDataGen()) {
      try {
        operation.call(dataGeneratorConfig);
      } catch (Throwable throwable) {
        PaucalMod.LOGGER.error("Data generation failed", throwable);
      } finally {
        System.exit(0);
      }
    } else {
      operation.call(dataGeneratorConfig);
    }
  }

  @Shadow
  public static boolean isRunningDataGen() {
    throw new RuntimeException();
  }
}
