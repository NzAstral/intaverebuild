package de.jpx3.intave.check.combat.heuristics.sample;

import java.io.Serializable;

public final class AttackEvent implements IdentityAssociated, Serializable {
  private final Identity target;

  public AttackEvent(Identity target) {
    this.target = target;
  }

  public Identity target() {
    return target;
  }

  @Override
  public Identity identity() {
    return target();
  }
}
