package at.petrak.paucal.api.mixin;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TagsProvider.class)
public interface AccessorTagsProvider<T> {
    @Invoker("getOrCreateRawBuilder")
    TagBuilder paucal$getOrCreateRawBuilder(TagKey<T> tag);
}
