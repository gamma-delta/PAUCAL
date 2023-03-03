package at.petrak.paucal.common.advancement;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.ContributorsManifest;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

public class BeContributorTrigger extends SimpleCriterionTrigger<BeContributorTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(PaucalAPI.MOD_ID, "login_as_patron");

    private static final String TAG_PATRON_LEVEL = "patron_level",
        TAG_IS_DEV = "is_dev";

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite predicate,
        DeserializationContext ctx) {
        var isDev = json.has(TAG_IS_DEV) ? GsonHelper.getAsBoolean(json, TAG_IS_DEV) : null;
        return new Instance(predicate, MinMaxBounds.Ints.fromJson(json.get(TAG_PATRON_LEVEL)), isDev);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        super.trigger(player, inst -> {
            var uuid = player.getUUID();
            var profile = ContributorsManifest.getContributor(uuid);
            if (profile == null) {
                return false;
            }

            return inst.patronLevel.matches(profile.getLevel())
                && (inst.isDev == null || inst.isDev == profile.isDev());
        });
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        protected final MinMaxBounds.Ints patronLevel;
        @Nullable
        protected final Boolean isDev;

        public Instance(EntityPredicate.Composite predicate, MinMaxBounds.Ints patronLevel, @Nullable Boolean isDev) {
            super(ID, predicate);
            this.patronLevel = patronLevel;
            this.isDev = isDev;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext ctx) {
            var json = super.serializeToJson(ctx);
            if (!this.patronLevel.isAny()) {
                json.add(TAG_PATRON_LEVEL, this.patronLevel.serializeToJson());
            }
            if (this.isDev != null) {
                json.addProperty(TAG_IS_DEV, this.isDev);
            }
            return json;
        }
    }
}
