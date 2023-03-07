package at.petrak.paucal.api.contrib;

import at.petrak.paucal.common.msg.MsgHeadpatSoundS2C;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HeadpatSpec {
    protected final String location;
    protected final Type type;

    protected HeadpatSpec(String location) {
        this.location = location;

        if (location.contains(":") && ResourceLocation.isValidResourceLocation(location)) {
            this.type = Type.VANILLA;
        } else {
            this.type = Type.GITHUB;
        }
    }

    public static List<HeadpatSpec> loadFromJson(JsonElement element) {
        if (element == null) {
            return List.of();
        } else if (GsonHelper.isStringValue(element)) {
            var loc = element.getAsString();
            var single = new HeadpatSpec(loc);
            return List.of(single);
        } else if (element instanceof JsonArray arr) {
            var out = new ArrayList<HeadpatSpec>();
            for (var elt : arr) {
                if (GsonHelper.isStringValue(elt)) {
                    out.add(new HeadpatSpec(elt.getAsString()));
                } else {
                    throw new RuntimeException("Invalid entry in the headpat spec, expected list of strings");
                }
            }
            return out;
        }

        throw new RuntimeException("Invalid entry in the headpat spec, expected list of strings");
    }

    public MsgHeadpatSoundS2C makePacket(Vec3 pos, float pitch, @Nullable Player patter) {
        return new MsgHeadpatSoundS2C(this.location, this.type == Type.GITHUB,
            pos.x, pos.y, pos.z, pitch,
            patter == null ? null : patter.getUUID());
    }

    public enum Type {
        VANILLA,
        GITHUB,
    }
}
