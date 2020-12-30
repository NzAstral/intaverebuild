package de.jpx3.intave.world.collision;

import de.jpx3.intave.tools.wrapper.WrappedAxisAlignedBB;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public final class CollisionFactory {
  private static final List<String> blockIntersectionExclusionNames = Arrays.asList(
    "ANVIL", "CARPET", "CARROT", "CHEST", "CROPS", "ENDER_CHEST", "GRASS_PATH",
    "IRON_FENCE", "KELP_PLANT", "LADDER", "LILY_PAD", "PISTON_MOVING_PIECE",
    "POTATO", "SEAGRASS", "SOIL", "TALL_SEAGRASS", "TRAPPED_CHEST", "VINE",
    "WATER", "WATER_LILY", "WHEAT"
  );

  public static List<WrappedAxisAlignedBB> getCollisionBoxes(
    Player player,
    WrappedAxisAlignedBB boundingBox
  ) {
    AbstractCollisionDefaultResolver collisionResolver = CollisionEngine.collisionResolver();
    return collisionResolver.getCollisionBoxes(player, boundingBox);
  }

  public static List<String> blockIntersectionExclusionNames() {
    return blockIntersectionExclusionNames;
  }
}