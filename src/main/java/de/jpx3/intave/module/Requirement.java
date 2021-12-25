package de.jpx3.intave.module;

public interface Requirement {
  boolean fulfilled();

  default Requirement and(Requirement requirement) {
    return () -> fulfilled() && requirement.fulfilled();
  }

  default Requirement or(Requirement requirement) {
    return () -> fulfilled() || requirement.fulfilled();
  }
}
