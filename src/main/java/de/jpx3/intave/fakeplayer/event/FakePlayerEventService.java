package de.jpx3.intave.fakeplayer.event;

import de.jpx3.intave.IntavePlugin;

public final class FakePlayerEventService {
  private final IntavePlugin plugin;
  private EntityVelocityCache entityVelocityCache;

  public FakePlayerEventService(IntavePlugin plugin) {
    this.plugin = plugin;
  }

  public void setup() {
    new PlayerPingPacketDispatcher(plugin);
    this.entityVelocityCache = new EntityVelocityCache(plugin);
  }

  public EntityVelocityCache entityVelocityCache() {
    return entityVelocityCache;
  }
}