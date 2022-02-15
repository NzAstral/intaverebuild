package de.jpx3.intave.user.storage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerStorage implements Storage {
  private final static int STORAGE_VERSION = 1;

  private final Map<Class<? extends Storage>, Storage> subStorages = new ConcurrentHashMap<>();
  private final UUID id;
  private long creation;

  PlayerStorage(UUID id) {
    this.id = id;
    this.creation = System.currentTimeMillis();
  }

  @Override
  public void writeTo(ByteArrayDataOutput output) {
    output.writeByte(STORAGE_VERSION);
    output.writeLong(id.getMostSignificantBits());
    output.writeLong(id.getLeastSignificantBits());
    output.writeLong(creation);
    subStorages.values().forEach(child -> child.writeTo(output));
  }

  private final static String INVALID_ID_ERROR = "Invalid entry fetched, expected %s but received id %s";

  @Override
  public void readFrom(ByteArrayDataInput input) {
    int version = input.readByte();
    if (version != STORAGE_VERSION) {
      return;
    }
    long mostSigBits = input.readLong();
    long leastSigBits = input.readLong();
    UUID id = new UUID(mostSigBits, leastSigBits);
    if (!id.equals(this.id)) {
      String errorMessage = String.format(INVALID_ID_ERROR, this.id, id);
      throw new IllegalStateException(errorMessage);
    }
    creation = input.readLong();
    subStorages.values().forEach(child -> child.readFrom(input));
  }

  <T extends Storage> void append(Class<T> storageClass) {
    subStorages.put(storageClass, instanceOf(storageClass));
  }

  public <T extends Storage> T storageOf(Class<T> storageClass) {
    //noinspection unchecked
    return (T) subStorages.get(storageClass);
  }

  private <T> T instanceOf(Class<T> tClass) {
    try {
      return tClass.newInstance();
    } catch (IllegalAccessException | InstantiationException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
