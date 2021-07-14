package de.jpx3.intave.event.feedback;

import org.bukkit.entity.Player;

public interface Callback<T> {
  void success(Player player, T target);
}