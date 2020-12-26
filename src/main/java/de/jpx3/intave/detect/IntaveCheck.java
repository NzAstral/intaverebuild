package de.jpx3.intave.detect;

import com.google.common.collect.Lists;
import de.jpx3.intave.user.UserCustomCheckMeta;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class IntaveCheck implements EventProcessor {
  private final String checkName;
  private final String configurationName;

  public IntaveCheck(String checkName, String configurationName) {
    this.checkName = checkName;
    this.configurationName = configurationName;
  }

  public boolean enabled() {
    return true;
  }

  public List<IntaveCheckPart> intaveCheckParts() {
    return Lists.newArrayList();
  }

  public String name() {
    return checkName;
  }
}