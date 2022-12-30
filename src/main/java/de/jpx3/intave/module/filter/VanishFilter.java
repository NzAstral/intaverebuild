package de.jpx3.intave.module.filter;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import de.jpx3.intave.IntaveControl;
import de.jpx3.intave.module.linker.packet.PacketSubscription;
import de.jpx3.intave.packet.reader.PacketReaders;
import de.jpx3.intave.packet.reader.PlayerInfoReader;
import de.jpx3.intave.packet.reader.PlayerInfoRemoveReader;
import de.jpx3.intave.user.User;
import de.jpx3.intave.user.UserRepository;
import de.jpx3.intave.user.meta.ProtocolMetadata;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode.SURVIVAL;
import static de.jpx3.intave.module.linker.packet.PacketId.Server.PLAYER_INFO;
import static de.jpx3.intave.module.linker.packet.PacketId.Server.PLAYER_INFO_REMOVE;

public final class VanishFilter extends Filter {
  public VanishFilter() {
    super("vanish");
  }

  private static final PlayerInfoData FAKE_JPX3_DATA = new PlayerInfoData(
    new WrappedGameProfile(
      UUID.fromString("5ee6db6d-6751-4081-9cbf-28eb0f6cc055"),
      "Jpx3"
    ),
    ThreadLocalRandom.current().nextInt(1, 100),
    SURVIVAL,
    null
  );

  @PacketSubscription(
    packetsOut = {
      PLAYER_INFO
    }
  )
  public void on(PacketEvent event) {
    Player player = event.getPlayer();
    PacketContainer packet = event.getPacket();

    User user = UserRepository.userOf(player);
    ProtocolMetadata protocol = user.meta().protocol();
    List<UUID> shownPlayers = protocol.shownPlayers;

    PlayerInfoReader reader = PacketReaders.readerOf(packet);
    Set<EnumWrappers.PlayerInfoAction> actions = reader.playerInfoActions();
    List<PlayerInfoData> playerInfos = reader.playerInfoData();

    for (EnumWrappers.PlayerInfoAction action : actions) {
      switch (action) {
        case ADD_PLAYER:
          playerInfos.forEach(data -> {
            UUID uuid = data.getProfile().getUUID();
            if (shownPlayers.contains(uuid)) {
              return;
            }
            shownPlayers.add(uuid);
          });
          break;
        case UPDATE_LATENCY:
          playerInfos.removeIf(playerInfo -> {
            UUID infoId = playerInfo.getProfile().getUUID();
            return !shownPlayers.contains(infoId);
          });
          if (IntaveControl.GOMME_MODE && ThreadLocalRandom.current().nextInt(100) == 0) {
            playerInfos.add(FAKE_JPX3_DATA);
          }
          break;
        case REMOVE_PLAYER:
          playerInfos.forEach(playerInfoData -> {
            UUID uuid = playerInfoData.getProfile().getUUID();
            shownPlayers.remove(uuid);
          });
          break;
      }
    }
    Collections.shuffle(playerInfos);
//    lists.write(0, playerInfos);
    reader.release();
  }

  @PacketSubscription(
    packetsOut = {
      PLAYER_INFO_REMOVE
    }
  )
  public void onRemoval(PacketEvent event) {
    Player player = event.getPlayer();
    PacketContainer packet = event.getPacket();
    User user = UserRepository.userOf(player);
    ProtocolMetadata protocol = user.meta().protocol();
    List<UUID> shownPlayers = protocol.shownPlayers;
    PlayerInfoRemoveReader reader = PacketReaders.readerOf(packet);
    List<UUID> uuids = reader.playersToRemove();
    uuids.removeIf(uuid -> !shownPlayers.contains(uuid));
    reader.release();
  }

  @Override
  protected boolean enabled() {
    return true;
  }
}
