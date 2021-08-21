package de.jpx3.intave.world.blockshape;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Map;

/**
 * Class generated using IntelliJ IDEA
 * Created by Richard Strunk 2021
 */

public interface OverrideBlockShapeAccess extends BlockShapeAccess {
  /**
   * Retrieves if this position is currently being overridden
   * @param posX the x coordinate of the selected block
   * @param posY the y coordinate of the selected block
   * @param posZ the z coordinate of the selected block
   * @return whether the block is currently in override
   */
  boolean currentlyInOverride(int posX, int posY, int posZ);

  /**
   * Retrieve the blocks override
   * @param posX the x coordinate of the selected block
   * @param posY the y coordinate of the selected block
   * @param posZ the z coordinate of the selected block
   * @return the override's blockshape
   */
  BlockShape overrideOf(int posX, int posY, int posZ);

  /**
   * Remove a blocks override
   * @param posX the x coordinate of the selected block
   * @param posY the y coordinate of the selected block
   * @param posZ the z coordinate of the selected block
   */
  void invalidateOverride(int posX, int posY, int posZ);

  /**
   * Retrieve all overrides/replacements as a {@link Location} to {@link BlockShape} {@link Map}.
   * The {@link Location} is the of the block
   * @return the located replacements
   */
  Map<Location, BlockShape> locatedReplacements();

  /**
   * Retrieve all overrides/replacements as a {@link Long} key to {@link BlockShape} {@link Map}.
   * The {@link Long} key can not be (re-)interpreted as a players position.
   * @return the indexed replacements
   */
  Map<Long, BlockShape> indexedReplacements();

  /**
   * Override a block at a specific position with a custom type and variant
   * @param world the world
   * @param posX the x coordinate of the selected block
   * @param posY the y coordinate of the selected block
   * @param posZ the z coordinate of the selected block
   * @param type the selected type
   * @param variant the selected variant
   */
  void override(World world, int posX, int posY, int posZ, Material type, int variant);

  void invalidateOverridesInBounds(int chunkXMinPos, int chunkXMaxPos, int chunkZMinPos, int chunkZMaxPos);
}
