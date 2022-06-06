package de.jpx3.intave.module.emulate;

import de.jpx3.intave.check.CheckConfiguration.CheckSettings;
import de.jpx3.intave.check.movement.Physics;
import de.jpx3.intave.module.Module;

public final class MovementEmulation extends Module {
  private boolean enabled;
  private int activationThreshold;
  private int ticks;
  private int ticksForHardSync;

  @Override
  public void enable() {
    CheckSettings configuration = plugin.checks().searchCheck(Physics.class)
      .configuration().settings();

    if (configuration.has("speculative-forward-emulation")) {
      enabled = configuration.boolBy("speculative-forward-emulation.enabled", false);
      activationThreshold = configuration.intBy("speculative-forward-emulation.activation-threshold", 0);
      ticks = configuration.intBy("speculative-forward-emulation.ticks", 0);
      ticksForHardSync = configuration.intBy("speculative-forward-emulation.ticks-for-hard-sync", 0);
    } else {
      enabled = false;
    }
  }
}
