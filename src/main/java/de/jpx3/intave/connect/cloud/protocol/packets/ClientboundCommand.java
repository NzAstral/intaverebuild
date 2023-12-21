package de.jpx3.intave.connect.cloud.protocol.packets;

import de.jpx3.intave.connect.cloud.protocol.BinaryPacket;
import de.jpx3.intave.connect.cloud.protocol.Direction;
import de.jpx3.intave.connect.cloud.protocol.listener.Clientbound;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ClientboundCommand extends BinaryPacket<Clientbound> {
  private CommandType type;
  private String key;

  public ClientboundCommand() {
    super(Direction.CLIENTBOUND, "COMMAND", "1");
  }

  @Override
  public void serialize(DataOutput buffer) {
    try {
      buffer.writeUTF(type.name());
      buffer.writeUTF(key);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deserialize(DataInput buffer) {
    try {
      type = CommandType.valueOf(buffer.readUTF());
      key = buffer.readUTF();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public CommandType type() {
    return type;
  }

  public String key() {
    return key;
  }

  public enum CommandType {
    SHUTDOWN,
  }
}
