package de.jpx3.intave.detect;

import de.jpx3.intave.user.UserCustomCheckMeta;
import de.jpx3.intave.user.UserRepository;
import org.bukkit.entity.Player;

public abstract class IntaveMetaCheck<META extends UserCustomCheckMeta> extends IntaveCheck {
  private final Class<? extends UserCustomCheckMeta> metaClass;

  public IntaveMetaCheck(String checkName, String configurationName, Class<? extends UserCustomCheckMeta> metaClass) {
    super(checkName, configurationName);
    this.metaClass = metaClass;
  }

  public META metaOf(Player player) {
    //noinspection unchecked
    return (META) UserRepository.userOf(player).customMeta(metaClass);
  }
}
