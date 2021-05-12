package de.jpx3.intave.diagnostics;

public final class BoundingBoxAccessFlowStudy {

  // total number of bb access
  public static int requests;

  // total number of required lookups
  public static int lookups;

  // total number of lookups, that didn't need to "ask the server"
  public static int dynamic;

  // NB gc = global cache
  //
  // lookup flow,
  //   green = lookup via gc
  //   yellow = created new gc entry, manual lookup
  //   red = cache denied, manual lookup
  public static int green, yellow, red;

  public static void increaseRequests() {
    requests++;
  }

  public static void increaseLookups() {
    lookups++;
  }

  public static void increaseDynamic() {
    dynamic++;
  }

  public static void increaseGreenLookups() {
    green++;
  }

  public static void increaseYellowLookups() {
    yellow++;
  }

  public static void increaseRedLookups() {
    red++;
  }

  public static String output() {
    return requests + " requests required " + lookups + " lookups, " + green + " green, " + yellow + " yellow, " + red + " red, " + dynamic + " dynamic";
  }
}
