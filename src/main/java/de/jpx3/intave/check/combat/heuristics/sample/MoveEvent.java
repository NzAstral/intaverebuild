package de.jpx3.intave.check.combat.heuristics.sample;

import de.jpx3.intave.shade.Position;
import de.jpx3.intave.shade.Rotation;

import java.io.Serializable;

public final class MoveEvent extends Event implements Serializable {
  private final Position position;
  private final Rotation rotation;

  private MoveEvent(Position position, Rotation rotation) {
    this.position = position;
    this.rotation = rotation;
  }

  public Position position() {
    return position;
  }

  public Rotation rotation() {
    return rotation;
  }

  public static MoveEvent of(Position position, Rotation rotation) {
    return new MoveEvent(position, rotation);
  }
}
