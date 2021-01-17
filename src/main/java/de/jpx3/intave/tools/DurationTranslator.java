package de.jpx3.intave.tools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class DurationTranslator {

  public static String translateDuration(long duration) {
    if(duration <= 0) {
      return "invalid";
    }
    // wunderschön, oder nicht?
    List<TimeUnit> best = Arrays.stream(TimeUnit.values())
      .filter(timeUnit -> TimeUnit.MILLISECONDS.convert(duration, timeUnit) > 1)
      .sorted(Comparator.<TimeUnit>comparingLong(o -> TimeUnit.MILLISECONDS.convert(duration, o)).reversed())
      .collect(Collectors.toList());
    TimeUnit leadingTimeUnit = best.get(0);
    TimeUnit secondLeadingTimeUnit = best.get(1);
    String firstType = translateType(leadingTimeUnit, duration);
    String secondType = translateType(secondLeadingTimeUnit, duration);
    return firstType + (firstType.isEmpty() ? "" : " and ") + secondType;
  }

  private static String translateType(TimeUnit unit, long duration) {
    long convert = unit.convert(duration, TimeUnit.MILLISECONDS);
    if(convert == 0) {
      return "";
    }
    String name = unit.name().toLowerCase();
    return (convert == 1 ? "one" : convert) + " " + name.substring(0, name.length() - (convert == 1 ? 1 : 0));
  }
}
