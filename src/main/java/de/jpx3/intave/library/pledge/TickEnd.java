package de.jpx3.intave.library.pledge;

import java.util.ArrayList;
import java.util.List;

public class TickEnd {
  private static final List<Runnable> tickEndSubscribers = new ArrayList<>();
  private static TickEndTask task;

  public static void start() {
    task = TickEndTask.create(() -> {
      tickEndSubscribers.forEach(Runnable::run);
    });
  }

  public static void subscribe(Runnable runnable) {
    tickEndSubscribers.add(runnable);
  }

  public static void unsubscribe(Runnable runnable) {
    tickEndSubscribers.remove(runnable);
  }

  public static void stop() {
    task.cancel();
  }
}
