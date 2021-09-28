package de.jpx3.intave.check.movement.physics;

import de.jpx3.intave.user.User;
import de.jpx3.intave.user.UserLocal;

public final class SimulationStack {
  private final static UserLocal<SimulationStack> stackUserLocal = UserLocal.withInitial(SimulationStack::new);

  private final static int DEFAULT_DISTANCE = Integer.MAX_VALUE;

  private Simulation collisionResult;
  private int forward, strafe;
  private boolean jumped;
  private boolean sprinted;
  private boolean reduced;
  private double smallestDistance;
  private boolean handActive;

  public SimulationStack() {
    this.smallestDistance = DEFAULT_DISTANCE;
  }

  public void restore() {
    collisionResult = null;
    forward = 0;
    strafe = 0;
    jumped = false;
    reduced = false;
    smallestDistance = DEFAULT_DISTANCE;
    handActive = false;
  }

  public void tryAppendToState(
    Simulation simulation,
    double newDistance,
    MovementConfiguration movementConfiguration
  ) {
    if (newDistance < this.smallestDistance) {
      appendToState(simulation, newDistance, movementConfiguration);
    }
  }

  private void appendToState(
    Simulation collisionResult,
    double newDistance,
    MovementConfiguration movementConfiguration
  ) {
    this.collisionResult = collisionResult;
    this.smallestDistance = newDistance;
    this.forward = movementConfiguration.forward();
    this.strafe = movementConfiguration.strafe();
    this.reduced = movementConfiguration.isReducing();
    this.sprinted = movementConfiguration.isSprinting();
    this.jumped = movementConfiguration.isJumping();
    this.handActive = movementConfiguration.isHandActive();
  }

  public boolean noMatch() {
    return collisionResult == null || this.smallestDistance == DEFAULT_DISTANCE;
  }

  public Simulation bestSimulation() {
    return collisionResult;
  }

  public int forward() {
    return forward;
  }

  public int strafe() {
    return strafe;
  }

  public boolean jumped() {
    return jumped;
  }

  public boolean sprinted() {
    return sprinted;
  }

  public boolean reduced() {
    return reduced;
  }

  public double smallestDistance() {
    return smallestDistance;
  }

  public boolean handActive() {
    return handActive;
  }

  public static SimulationStack of(User user) {
    SimulationStack simulationStack = stackUserLocal.get(user);
    simulationStack.restore();
    return simulationStack;
  }
}
