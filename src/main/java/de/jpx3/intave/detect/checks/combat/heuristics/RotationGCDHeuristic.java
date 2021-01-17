package de.jpx3.intave.detect.checks.combat.heuristics;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import de.jpx3.intave.detect.IntaveMetaCheckPart;
import de.jpx3.intave.detect.checks.combat.Heuristics;
import de.jpx3.intave.event.packet.PacketDescriptor;
import de.jpx3.intave.event.packet.PacketSubscription;
import de.jpx3.intave.event.packet.Sender;
import de.jpx3.intave.user.User;
import de.jpx3.intave.user.UserCustomCheckMeta;
import org.bukkit.entity.Player;

import java.util.List;

import static de.jpx3.intave.detect.checks.combat.heuristics.Anomaly.AnomalyOption.*;

public final class RotationGCDHeuristic extends IntaveMetaCheckPart<Heuristics, RotationGCDHeuristic.RotationGCDMeta> {

  public RotationGCDHeuristic(Heuristics parentCheck) {
    super(parentCheck, RotationGCDMeta.class);
  }

  @PacketSubscription(
    packets = {
      @PacketDescriptor(sender = Sender.CLIENT, packetName = "LOOK"),
      @PacketDescriptor(sender = Sender.CLIENT, packetName = "POSITION_LOOK"),
    }
  )
  public void rotationCheck(PacketEvent event) {
    Player player = event.getPlayer();
    PacketContainer packetContainer = event.getPacket();

    User user = userOf(player);
    RotationGCDMeta rotationGCDMeta = metaOf(user);

    List<Float> packetFloats = packetContainer.getFloat().getValues();
    float newYaw = packetFloats.get(0);
    float newPitch = packetFloats.get(1);
    float oldYaw = rotationGCDMeta.lastYaw;
    float oldPitch = rotationGCDMeta.lastPitch;

    float yawDiff = Math.abs(newYaw - oldYaw);
    float pitchDiff = Math.abs(newPitch - oldPitch);

    // old liquidbounce gcd patch
    // detects a few clients
    if(pitchDiff > 0 && yawDiff > 0) {
      int yawDecimal = decimalPlacesOf(newYaw);
      int pitchDecimal = decimalPlacesOf(newPitch);

      if(yawDecimal < 4 && pitchDecimal < 3) {
        // vl+
        rotationGCDMeta.decimalVL++;
        if(rotationGCDMeta.decimalVL > 30) {
          rotationGCDMeta.decimalVL = 0;
          parentCheck().saveAnomaly(
            player,
            Anomaly.anomalyOf(
              Confidence.PROBABLE,
              Anomaly.Type.KILLAURA,
              "rotations have too few decimals",
              LIMIT_2 | DELAY_16s | SUGGEST_MINING
            )
          );
        }
      } else if(rotationGCDMeta.decimalVL > 0) {
        // vl-
      }
    }


    // -> add gcd checks
  }

  private static strictfp int decimalPlacesOf(float value) {
    String s = Float.toString(value);
    s = s.substring(s.indexOf(".") + 1);
    return s.length();
  }

  public static class RotationGCDMeta extends UserCustomCheckMeta {
    public float lastYaw;
    public float lastPitch;
    public int decimalVL;
  }
}
