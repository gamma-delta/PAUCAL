package at.petrak.paucal.common.advancement;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.Contributors;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

public class BeContributorTrigger extends SimpleCriterionTrigger<BeContributorTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(PaucalAPI.MOD_ID, "login_as_patron");

    private static final String TAG_PATRON_LEVEL = "patron_level",
        TAG_IS_DEV = "is_dev",
        TAG_IS_COOL = "is_cool";

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite predicate,
        DeserializationContext ctx) {
        var isDev = json.has(TAG_IS_DEV) ? GsonHelper.getAsBoolean(json, TAG_IS_DEV) : null;
        var isCool = json.has(TAG_IS_COOL) ? GsonHelper.getAsBoolean(json, TAG_IS_COOL) : null;
        return new Instance(predicate, MinMaxBounds.Ints.fromJson(json.get(TAG_PATRON_LEVEL)), isDev, isCool);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        super.trigger(player, inst -> {
            var uuid = player.getUUID();
            var profile = Contributors.getContributor(uuid);
            if (profile == null) {
                return false;
            }
            var type = profile.getContributorType();

            return inst.patronLevel.matches(type.level())
                && (inst.isDev == null || inst.isDev == type.isDev())
                && (inst.isCool == null || inst.isCool == type.isCool());
        });
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        protected final MinMaxBounds.Ints patronLevel;
        @Nullable
        protected final Boolean isDev;
        @Nullable
        protected final Boolean isCool;

        public Instance(EntityPredicate.Composite predicate, MinMaxBounds.Ints patronLevel, @Nullable Boolean isDev,
            @Nullable Boolean isCool) {
            super(ID, predicate);
            this.patronLevel = patronLevel;
            this.isDev = isDev;
            this.isCool = isCool;
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
            if (this.isCool != null) {
                json.addProperty(TAG_IS_COOL, this.isCool);
            }
            return json;
        }
    }
}
