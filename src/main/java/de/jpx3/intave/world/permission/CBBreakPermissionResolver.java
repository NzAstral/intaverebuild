package de.jpx3.intave.world.permission;

import de.jpx3.intave.IntavePlugin;
import de.jpx3.intave.access.BlockBreakPermissionCheck;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public final class CBBreakPermissionResolver implements BlockBreakPermissionCheck {
  @Override
  public boolean hasPermission(Player player, Block block) {
    BlockBreakEvent blockBreakEvent = new PermissionCheckBlockBreakEvent(block, player);
    //Bukkit.getPluginManager().callEvent(blockBreakEvent);
    IntavePlugin.singletonInstance().eventLinker().fireExternalEvent(blockBreakEvent);
    return !blockBreakEvent.isCancelled();
  }

  public static class PermissionCheckBlockBreakEvent extends BlockBreakEvent {
    public PermissionCheckBlockBreakEvent(Block theBlock, Player player) {
      super(theBlock, player);
    }
  }
}
