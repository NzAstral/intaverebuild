package de.jpx3.intave.check.combat.heuristics.sample;

import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public final class Sample implements Serializable {
  private final Identity owner;
  private final Set<Identity> targets = new HashSet<>();
  private final List<List<Event>> events = new ArrayList<>();
  private List<Event> currentEvents = new ArrayList<>();
  private int current;

  private Sample(Identity owner) {
    this.owner = owner;
  }

  public void insert(Event event) {
    if (event == null) {
      return;
    }
    if (event instanceof IdentityAssociated) {
      targets.add(((IdentityAssociated) event).identity());
    }
    if (currentEvents == null) {
      currentEvents = new ArrayList<>();
    }
    currentEvents.add(event);
  }

  public void finishTick() {
    List<Event> currentEvents = this.currentEvents;
    events.add(currentEvents);
    this.currentEvents = null;
    current++;
  }

  public List<Event> eventsIn(int tick) {
    if (tick > length() || tick < 0) {
      throw new IllegalArgumentException(tick + " out of bounds [0;"+length()+"]");
    }
    return events.get(tick);
  }

  public int length() {
    return events.size();
  }

  public byte[] serialize() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(this);
    return compress(baos.toByteArray());
  }

  public static Sample fromSerialized(byte[] serialized) throws IOException, ClassNotFoundException {
    ByteArrayInputStream bais = new ByteArrayInputStream(decompress(serialized));
    ObjectInputStream objectInputStream = new ObjectInputStream(bais);
    return (Sample) objectInputStream.readObject();
  }

  public static byte[] compress(byte[] in) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      DeflaterOutputStream deflater = new DeflaterOutputStream(out);
      deflater.write(in);
      deflater.flush();
      deflater.close();
      return out.toByteArray();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public static byte[] decompress(byte[] in) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      InflaterOutputStream inflater = new InflaterOutputStream(out);
      inflater.write(in);
      inflater.flush();
      inflater.close();
      return out.toByteArray();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public static Sample newFor(Player player) {
    return new Sample(Identity.of(player));
  }
}
