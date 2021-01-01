package de.jpx3.intave.event;

import de.jpx3.intave.IntavePlugin;
import de.jpx3.intave.event.dispatch.MovementDispatcher;
import de.jpx3.intave.event.dispatch.PlayerAbilityEvaluator;
import de.jpx3.intave.event.dispatch.PlayerInventoryEvaluator;
import de.jpx3.intave.event.dispatch.PotionEffectEvaluator;
import de.jpx3.intave.event.service.MovementEmulationEngine;
import de.jpx3.intave.event.service.TransactionFeedbackService;
import de.jpx3.intave.event.service.entity.ClientSideEntityService;
import de.jpx3.intave.user.UserRepositoryEventListener;

public final class EventService {
  private final IntavePlugin plugin;
  private TransactionFeedbackService transactionFeedbackService;
  private MovementEmulationEngine emulationEngine;

  public EventService(IntavePlugin plugin) {
    this.plugin = plugin;
  }

  public void setup() {
    this.transactionFeedbackService = new TransactionFeedbackService(plugin);
    this.emulationEngine = new MovementEmulationEngine(plugin);
    new UserRepositoryEventListener(plugin);
    new MovementDispatcher(plugin);
    new PotionEffectEvaluator(plugin);
    new PlayerAbilityEvaluator(plugin);
    new PlayerInventoryEvaluator(plugin);
    new ClientSideEntityService(plugin);
  }
  
  public MovementEmulationEngine emulationEngine() {
    return emulationEngine;
  }

  public TransactionFeedbackService transactionFeedbackService() {
    return transactionFeedbackService;
  }
}