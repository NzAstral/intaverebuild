package de.jpx3.intave.detect.checks.world;

import de.jpx3.intave.IntavePlugin;
import de.jpx3.intave.detect.Check;
import de.jpx3.intave.detect.checks.world.breakspeedlimiter.CompletionDurationCheck;
import de.jpx3.intave.detect.checks.world.breakspeedlimiter.RestartCheck;

public final class BreakSpeedLimiter extends Check {
  public BreakSpeedLimiter(IntavePlugin plugin) {
    super("BreakSpeedLimiter", "breakspeedlimiter");
    setupParts();
  }

  public void setupParts() {
    appendCheckPart(new CompletionDurationCheck(this));
    appendCheckPart(new RestartCheck(this));
  }
}