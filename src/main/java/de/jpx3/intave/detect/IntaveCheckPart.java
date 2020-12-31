package de.jpx3.intave.detect;

public abstract class IntaveCheckPart<P extends IntaveCheck> implements EventProcessor {
  private final P parentCheck;

  public IntaveCheckPart(P parentCheck) {
    this.parentCheck = parentCheck;
  }

  public P parentCheck() {
    return parentCheck;
  }

  public boolean enabled() {
    return true;
  }
}