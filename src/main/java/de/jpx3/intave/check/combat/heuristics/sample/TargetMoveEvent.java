package de.jpx3.intave.check.combat.heuristics.sample;

import de.jpx3.intave.shade.Position;
import de.jpx3.intave.shade.Rotation;

public final class TargetMoveEvent extends Event implements IdentityAssociated {
  private final Identity identity;
  private final Position position;
  private final Rotation rotation;

  private TargetMoveEvent(
    Identity identity,
    Position position,
    Rotation rotation
  ) {
    this.identity = identity;
    this.position = position;
    this.rotation = rotation;
  }

  public Identity identity() {
    return identity;
  }

  public Position position() {
    return position;
  }

  public Rotation rotation() {
    return rotation;
  }

  public static TargetMoveEvent of(
    Identity identity,
    Position position,
    Rotation rotation) {
    return new TargetMoveEvent(identity, position, rotation);
  }
}
