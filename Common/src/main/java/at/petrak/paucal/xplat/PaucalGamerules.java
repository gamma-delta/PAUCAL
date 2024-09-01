package at.petrak.paucal.xplat;


import net.minecraft.world.level.GameRules;

// extend gamerules just so i don't have to GameRules.{} everything
public class PaucalGamerules extends GameRules {
  public static final Key<BooleanValue> ALLOW_HEADPATS =
      register("paucal:allowHeadpats", Category.PLAYER, BooleanValue.create(true));

  public static void init() {
    // static init
  }
}
