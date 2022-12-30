package de.jpx3.intave.packet.reader;

import com.comphenix.protocol.wrappers.Converters;

import java.util.List;
import java.util.UUID;

public final class PlayerInfoRemoveReader extends AbstractPacketReader {
  public List<UUID> playersToRemove() {
    return packet().getLists(Converters.passthrough(UUID.class)).read(0);
  }
}
