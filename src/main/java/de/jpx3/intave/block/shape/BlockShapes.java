package de.jpx3.intave.block.shape;

import de.jpx3.intave.shade.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class BlockShapes {
  private static final BlockShape EMPTY = new EmptyBlockShape();

  public static BlockShape emptyShape() {
    return EMPTY;
  }

  public static BlockShape originCube() {
    return new CubeShape(0, 0, 0);
  }

  public static BlockShape cubeAt(int posX, int posY, int posZ) {
    return new CubeShape(posX, posY, posZ);
  }

  public static BlockShape shapeOf(@NotNull List<BoundingBox> boundingBoxes) {
    switch (boundingBoxes.size()) {
      case 0:
        return emptyShape();
      case 1:
        return boundingBoxes.get(0);
      case 2:
        return new MergeBlockShape(boundingBoxes.get(0), boundingBoxes.get(1));
      default:
        return new ArrayBlockShape(new ArrayList<>(boundingBoxes));
    }
  }

  public static BlockShape merge(BlockShape... boundingBoxes) {
    return new ArrayBlockShape(Arrays.asList(boundingBoxes));
  }

  public static BlockShape merge(@NotNull BlockShape shapeA, @NotNull BlockShape shapeB) {
    if (shapeA.isEmpty()) {
      return shapeB;
    }
    if (shapeB.isEmpty()) {
      return shapeA;
    }
    if (shapeA == shapeB) {
      return shapeA;
    }
    return new MergeBlockShape(shapeA, shapeB);
  }

  public static Function<@Nullable List<BlockShape>, @NotNull BlockShape> mergeShapes() {
    return shapes -> {
      if (shapes == null) {
        return emptyShape();
      }
      switch (shapes.size()) {
        case 0:
          return emptyShape();
        case 1:
          return shapes.get(0);
        case 2:
          return merge(shapes.get(0), shapes.get(1));
        default:
          return new ArrayBlockShape(shapes);
      }
    };
  }
}
