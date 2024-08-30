package at.petrak.paucal.xplat.common.advancement;

import at.petrak.paucal.xplat.api.PaucalAPI;
import at.petrak.paucal.xplat.common.ContributorsManifest;
import at.petrak.paucal.xplat.common.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class BeContributorTrigger extends SimpleCriterionTrigger<BeContributorTrigger.Instance> {
  private static final ResourceLocation ID = PaucalAPI.modLoc("login_as_patron");

  public static final Codec<Instance> CODEC = RecordCodecBuilder.create(i -> i.group(
      EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
      MinMaxBounds.Ints.CODEC.fieldOf("patron_level").forGetter(Instance::patronLevel),
      Codec.BOOL.optionalFieldOf("is_dev").forGetter(Instance::isDev)
  ).apply(i, Instance::new));

  public void trigger(ServerPlayer player) {
    super.trigger(player, inst -> {
      var uuid = player.getUUID();
      var profile = ContributorsManifest.getContributor(uuid);
      if (profile == null) {
        return false;
      }

      return inst.patronLevel.matches(profile.getLevel())
          && (inst.isDev.isEmpty() || inst.isDev.get() == profile.isDev());
    });
  }

  @Override
  public Codec<Instance> codec() {
    return CODEC;
  }

  public record Instance(Optional<ContextAwarePredicate> player,
                         MinMaxBounds.Ints patronLevel,
                         Optional<Boolean> isDev)
      implements SimpleCriterionTrigger.SimpleInstance {
    public Criterion<Instance> criterion() {
      return ModRegistries.BE_CONTRIBUTOR_TRIGGER.get().createCriterion(this);
    }
  }
}
