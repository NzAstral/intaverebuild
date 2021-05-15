package de.jpx3.intave.tools.packet;

import com.comphenix.protocol.events.PacketContainer;
import de.jpx3.intave.reflect.ReflectiveAccess;

public final class PlayerActionResolver {
  private final static Class<?> SERVER_CLASS = ReflectiveAccess.lookupServerClass("PacketPlayInEntityAction$EnumPlayerAction");

  public static PlayerAction resolveActionFromPacket(PacketContainer packet) {
    return packet.getEnumModifier(PlayerAction.class, SERVER_CLASS).read(0);
  }
}