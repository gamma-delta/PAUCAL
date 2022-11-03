package at.petrak.paucal.api.datagen;

import at.petrak.paucal.api.PaucalAPI;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public abstract class PaucalAdvancementProvider extends AdvancementProvider {
    private static final Gson PAUCAL_GSON = (new GsonBuilder()).setPrettyPrinting().create();

    protected final String modid;
    public final DataGenerator generator;

    public PaucalAdvancementProvider(DataGenerator generatorIn, String modid) {
        super(generatorIn);
        this.generator = generatorIn;
        this.modid = modid;
    }

    // [VanillaCopy] mostly again
    @Override
    public void run(CachedOutput cache) {
        Path outputFolder = this.generator.getOutputFolder();
        Set<ResourceLocation> $$2 = Sets.newHashSet();
        Consumer<Advancement> consumer = ($$3x) -> {
            if (!$$2.add($$3x.getId())) {
                throw new IllegalStateException("Duplicate advancement " + $$3x.getId());
            } else {
                Path path = paucalCreatePath(outputFolder, $$3x);

                try {
                    DataProvider.saveStable(cache, $$3x.deconstruct().serializeToJson(), path);
                } catch (IOException exn) {
                    PaucalAPI.LOGGER.error("{} couldn't save advancement {}", this.getClass().getSimpleName(), path,
                        exn);
                }

            }
        };
        this.makeAdvancements(consumer);
    }

    protected abstract void makeAdvancements(Consumer<Advancement> consumer);


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

    private static Path paucalCreatePath(Path $$0, Advancement $$1) {
        return $$0.resolve("data/" + $$1.getId().getNamespace() + "/advancements/" + $$1.getId().getPath() + ".json");
    }
}
