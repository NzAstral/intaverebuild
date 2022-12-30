package de.jpx3.intave.module.actionbar;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.jpx3.intave.executor.TaskTracker;
import de.jpx3.intave.module.Module;
import de.jpx3.intave.module.Modules;
import de.jpx3.intave.packet.PacketSender;
import de.jpx3.intave.user.User;
import de.jpx3.intave.user.UserRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ActionBarDisplayer extends Module {
  private final ClickFeeder clickFeeder = new ClickFeeder();
  private final Lock lock = new ReentrantLock();

  @Override
  public void enable() {
    Modules.linker().bukkitEvents().registerEventsIn(clickFeeder);
    Modules.linker().packetEvents().linkSubscriptionsIn(clickFeeder);
  }

  public void subscribe(User receiver, User target, DisplayType type) {
    if (!receiver.hasPlayer() || !target.hasPlayer()) {
      return;
    }
    try {
      lock.lock();
      receiver.setActionTarget(target.id());
      target.addActionReceiver(receiver.id(), type);
      startTaskFor(receiver);
    } finally {
      lock.unlock();
    }
  }

  public boolean inSubscription(User receiver) {
    return receiver.actionTarget() != null;
  }

  public void unsubscribe(User receiver) {
    try {
      lock.lock();
      UUID id = receiver.actionTarget();
      receiver.setActionTarget(null);
      User target = UserRepository.userOf(id);
      target.removeActionSubscription(receiver.id());
    } finally {
      lock.unlock();
    }
  }

  private void startTaskFor(User receiver) {
    if (!receiver.hasPlayer()) {
      return;
    }
    UUID target = receiver.actionTarget();
    int[] counter = {0};
    int[] taskId = new int[1];
    taskId[0] = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
      boolean cancelTask = counter[0]++ >= 20 * 60 * 15 || !receiver.hasPlayer() || !receiver.player().isOnline() || !inSubscription(receiver) || receiver.actionTarget() != target;
      User targetUser = UserRepository.userOf(target);
      cancelTask |= !targetUser.hasPlayer() || !targetUser.player().isOnline() || !targetUser.anyActionSubscriptions();
      if (cancelTask) {
//        System.out.println("CANCELLED " + counter[0] + " " + !receiver.hasPlayer() + " " + !receiver.player().isOnline() + " " + !inSubscription(receiver) + " " + (receiver.actionTarget() != target) + " " + !targetUser.hasPlayer() + " " + !targetUser.player().isOnline() + " " + !targetUser.anyActionSubscriptions());
        Bukkit.getScheduler().cancelTask(taskId[0]);
        TaskTracker.stopped(taskId[0]);
        unsubscribe(receiver);
        return;
      }
      String text = targetUser.actionDisplayOf(DisplayType.CLICKS);
      if (text != null) {
        sendActionBar(receiver.player(), text);
      }
    }, 1, 1);
    TaskTracker.begun(taskId[0]);
  }

  private void sendActionBar(Player player, String message) {
    PacketContainer packet = new PacketContainer(PacketType.Play.Server.CHAT);
    packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
    packet.getBytes().write(0, (byte) 2);
    PacketSender.sendServerPacket(player, packet);
//    Synchronizer.synchronize(() -> {
//      player.sendMessage(message);
//    });
  }

  @Override
  public void disable() {

  }
}
