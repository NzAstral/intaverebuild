package de.jpx3.intave.check.combat.heuristics.detect.experimental;

import de.jpx3.intave.user.meta.CheckCustomMetadata;

import java.util.ArrayList;
import java.util.List;

public class RotationPrevisionBlueprintMeta extends CheckCustomMetadata {
  protected final List<RotationData> rotationValues = new ArrayList<>();
  protected int lastAttack; // In client ticks

  public static class RotationData {
    protected final float yaw;
    protected final float yawDelta;
    protected final float expectedYawDelta;
    protected final float expectedYaw;

    protected final float pitch;
    protected final float pitchDelta;
    protected final float expectedPitchDelta;
    protected final float expectedPitch;

    public RotationData(RotationDataBuilder builder) {
      this.yaw = builder.yaw;
      this.yawDelta = builder.yawDelta;
      this.expectedYawDelta = builder.expectedYawDelta;
      this.expectedYaw = builder.expectedYaw;
      this.pitch = builder.pitch;
      this.pitchDelta = builder.pitchDelta;
      this.expectedPitchDelta = builder.expectedPitchDelta;
      this.expectedPitch = builder.expectedPitch;
    }

    public static class RotationDataBuilder {
      private float yaw;
      private float yawDelta;
      private float expectedYawDelta;
      private float expectedYaw;

      private float pitch;
      private float pitchDelta;
      private float expectedPitchDelta;
      private float expectedPitch;

      public RotationData build() {
        return new RotationData(this);
      }

      public RotationDataBuilder yaw(float yaw) {
        this.yaw = yaw;
        return this;
      }

      public RotationDataBuilder yawDelta(float yawDelta) {
        this.yawDelta = yawDelta;
        return this;
      }

      public RotationDataBuilder expectedYawDelta(float expectedYawDelta) {
        this.expectedYawDelta = expectedYawDelta;
        return this;
      }

      public RotationDataBuilder expectedYaw(float expectedYaw) {
        this.expectedYaw = expectedYaw;
        return this;
      }

      public RotationDataBuilder pitch(float pitch) {
        this.pitch = pitch;
        return this;
      }

      public RotationDataBuilder pitchDelta(float pitchDelta) {
        this.pitchDelta = pitchDelta;
        return this;
      }

      public RotationDataBuilder expectedPitchDelta(float expectedPitchDelta) {
        this.expectedPitchDelta = expectedPitchDelta;
        return this;
      }

      public RotationDataBuilder expectedPitch(float expectedPitch) {
        this.expectedPitch = expectedPitch;
        return this;
      }
    }
  }
}
