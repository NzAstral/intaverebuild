package de.jpx3.intave.detect.checks.world;

import com.google.common.collect.ImmutableList;
import de.jpx3.intave.IntavePlugin;
import de.jpx3.intave.detect.Check;
import de.jpx3.intave.detect.CheckPart;
import de.jpx3.intave.detect.checks.world.breakspeedlimiter.CompletionDurationCheck;
import de.jpx3.intave.detect.checks.world.breakspeedlimiter.RestartCheck;

import java.util.List;

public final class BreakSpeedLimiter extends Check {
  private final List<CheckPart<?>> checkParts;

  public BreakSpeedLimiter(IntavePlugin plugin) {
    super("BreakSpeedLimiter", "breakspeedlimiter");
    this.checkParts = ImmutableList.of(
      new CompletionDurationCheck(this),
      new RestartCheck(this)
    );
  }

  @Override
  public List<CheckPart<?>> checkParts() {
    return checkParts;
  }
}