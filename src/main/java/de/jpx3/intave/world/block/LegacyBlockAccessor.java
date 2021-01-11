package de.jpx3.intave.world.block;

import com.comphenix.protocol.wrappers.BlockPosition;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class LegacyBlockAccessor implements BlockAccessor {
  @Override
  public float blockDamage(Player player, ItemStack itemInHand, BlockPosition blockPosition) {
    World world = player.getWorld();
    WorldServer worldServer = ((CraftWorld) player.getWorld()).getHandle();
    Chunk chunk = worldServer.getChunkIfLoaded(blockPosition.getX() >> 4, blockPosition.getZ() >> 4);
    if(chunk == null) {
      return 0.0f;
    }
    net.minecraft.server.v1_8_R3.BlockPosition blockposition = new net.minecraft.server.v1_8_R3.BlockPosition(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    return chunk.getBlockData(blockposition).getBlock().getDamage(((CraftPlayer) player).getHandle(), worldServer, blockposition);
  }

  @Override
  public boolean replacementPlace(World world, BlockPosition blockPosition) {
    WorldServer worldServer = ((CraftWorld) world).getHandle();
    Chunk chunk = worldServer.getChunkIfLoaded(blockPosition.getX() >> 4, blockPosition.getZ() >> 4);
    if(chunk == null) {
      return false;
    }
    net.minecraft.server.v1_8_R3.BlockPosition blockposition = new net.minecraft.server.v1_8_R3.BlockPosition(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    return chunk.getBlockData(blockposition).getBlock().a(worldServer, blockposition);
  }
}
