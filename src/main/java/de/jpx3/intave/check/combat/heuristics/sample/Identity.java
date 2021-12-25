package de.jpx3.intave.check.combat.heuristics.sample;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public final class Identity implements Serializable {
  private final String name;
  private final UUID id;

  private Identity(String name, UUID id) {
    this.name = name;
    this.id = id;
  }

  public String name() {
    return name;
  }

  public UUID id() {
    return id;
  }

  public static Identity of(Player player) {
    return new Identity(player.getName(), player.getUniqueId());
  }
}
