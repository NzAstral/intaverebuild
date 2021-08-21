package de.jpx3.intave.world.blockshape;

/**
 * Class generated using IntelliJ IDEA
 * Created by Richard Strunk 2021
 */

public interface CachedBlockShapeAccess extends BlockShapeAccess {
  /**
   * Invalidate all caches
   */
  void identityInvalidate();

  /**
   * Invalidate resolver caches
   */
  void invalidate();

  /**
   * Invalidate all blocks around a specified position
   * @param posX the x coordinate of the selected block
   * @param posY the y coordinate of the selected block
   * @param posZ the z coordinate of the selected block
   */
  default void invalidate(int posX, int posY, int posZ) {
    invalidate0(posX + 1, posY, posZ);
    invalidate0(posX - 1, posY, posZ);
    invalidate0(posX, posY, posZ + 1);
    invalidate0(posX, posY, posZ - 1);
    invalidate0(posX, posY + 1, posZ);
    invalidate0(posX, posY - 1, posZ);
    invalidate0(posX, posY, posZ);
  }

  /**
   * Invalidate a specific block
   * @param posX the x coordinate of the selected block
   * @param posY the y coordinate of the selected block
   * @param posZ the z coordinate of the selected block
   */
  void invalidate0(int posX, int posY, int posZ);
}
