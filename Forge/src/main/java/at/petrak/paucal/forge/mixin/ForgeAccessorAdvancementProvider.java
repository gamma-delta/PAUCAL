package at.petrak.paucal.forge.mixin;

import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AdvancementProvider.class)
public interface ForgeAccessorAdvancementProvider {
    @Accessor("fileHelper")
    @Mutable
    void paucal$setFileHelper(ExistingFileHelper efh);
}
