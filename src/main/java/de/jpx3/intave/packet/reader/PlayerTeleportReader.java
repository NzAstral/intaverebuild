package de.jpx3.intave.packet.reader;

import com.comphenix.protocol.events.InternalStructure;
import de.jpx3.intave.adapter.MinecraftVersions;
import de.jpx3.intave.packet.TeleportFlag;
import org.bukkit.util.Vector;

import java.util.Set;

public final class PlayerTeleportReader extends AbstractPacketReader {
  private final static boolean VECTOR_ENCAPSULATION = MinecraftVersions.VER1_21_4.atOrAbove();

  public double positionX() {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      Vector position = posRot.getVectors().read(0);
      return position.getX();
    }
    return packet().getDoubles().read(0);
  }

  public void setPositionX(double x) {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      Vector position = posRot.getVectors().read(0);
      position.setX(x);
      posRot.getVectors().write(0, position);
    } else {
      packet().getDoubles().write(0, x);
    }
  }

  public double positionY() {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      Vector position = posRot.getVectors().read(0);
      return position.getY();
    }
    return packet().getDoubles().read(1);
  }

  public void setPositionY(double y) {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      Vector position = posRot.getVectors().read(0);
      position.setY(y);
      posRot.getVectors().write(0, position);
    } else {
      packet().getDoubles().write(1, y);
    }
  }

  public double positionZ() {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      Vector position = posRot.getVectors().read(0);
      return position.getZ();
    }
    return packet().getDoubles().read(2);
  }

  public void setPositionZ(double z) {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      Vector position = posRot.getVectors().read(0);
      position.setZ(z);
      posRot.getVectors().write(0, position);
    } else {
      packet().getDoubles().write(2, z);
    }
  }

  public float yaw() {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      return posRot.getFloat().read(0);
    }
    return packet().getFloat().read(0);
  }

  public void setYaw(float yaw) {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      posRot.getFloat().write(0, yaw);
    } else {
      packet().getFloat().write(0, yaw);
    }
  }

  public float pitch() {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      return posRot.getFloat().read(1);
    }
    return packet().getFloat().read(1);
  }

  public void setPitch(float pitch) {
    if (VECTOR_ENCAPSULATION) {
      InternalStructure posRot = packet().getStructures().read(0);
      posRot.getFloat().write(1, pitch);
    } else {
      packet().getFloat().write(1, pitch);
    }
  }

  public Set<TeleportFlag> flags() {
    return TeleportFlag.flagsFrom(packet());
  }

  public void setFlags(Set<TeleportFlag> flags) {
    TeleportFlag.writeFlags(packet(), flags);
  }
}
