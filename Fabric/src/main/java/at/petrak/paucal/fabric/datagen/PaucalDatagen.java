package at.petrak.paucal.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PaucalDatagen implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    var pack = fabricDataGenerator.createPack();
    if (System.getProperty("paucal.xplat_datagen") != null) {
      pack.addProvider(ModAdvancementGenerator::new);
    }
  }
}
