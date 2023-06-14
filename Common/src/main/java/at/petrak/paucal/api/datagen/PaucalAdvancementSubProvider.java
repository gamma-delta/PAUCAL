package at.petrak.paucal.api.datagen;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

/**
 * Post 1.19.4, the advancement provider is actually a thin wrapper around a list of these things.
 */
public abstract class PaucalAdvancementSubProvider implements AdvancementSubProvider {
    protected final String modid;

    protected PaucalAdvancementSubProvider(String modid) {
        this.modid = modid;
    }

    protected DisplayInfo simpleDisplay(ItemLike icon, String name, FrameType frameType) {
        return simpleDisplayWithBackground(icon, name, frameType, null);
    }

    protected DisplayInfo simpleDisplayWithBackground(ItemLike icon, String name, FrameType frameType,
        @Nullable ResourceLocation background) {
        return display(new ItemStack(icon), name, frameType, background, true, true, false);
    }

    protected DisplayInfo display(ItemStack icon, String name, FrameType frameType, ResourceLocation background,
        boolean showToast, boolean announceChat, boolean hidden) {
        String expandedName = "advancement." + this.modid + ":" + name;
        return new DisplayInfo(icon,
            Component.translatable(expandedName),
            Component.translatable(expandedName + ".desc"),
            background, frameType, showToast, announceChat, hidden);
    }

    protected String prefix(String name) {
        return this.modid + ":" + name;
    }

    protected ResourceLocation modLoc(String name) {
        return new ResourceLocation(modid, name);
    }
}
