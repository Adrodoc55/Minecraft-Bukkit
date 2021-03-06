package de.adrodoc55.minecraft.plugins.terrania.gs.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

import com.sk89q.worldguard.domains.DefaultDomain;

import de.adrodoc55.common.collections.Closure;
import de.adrodoc55.common.collections.CollectionUtils;
import de.adrodoc55.minecraft.plugins.common.command.CommandContext;
import de.adrodoc55.minecraft.plugins.common.command.Parameter;
import de.adrodoc55.minecraft.plugins.common.command.ParameterList;
import de.adrodoc55.minecraft.plugins.common.command.TabCompleteContext;
import de.adrodoc55.minecraft.plugins.common.utils.InsufficientPermissionException;
import de.adrodoc55.minecraft.plugins.common.utils.MinecraftUtils;
import de.adrodoc55.minecraft.plugins.terrania.gs.Grundstueck;

public class RemoveMemberGsCommand extends ConcreteGsCommand {

  private static final String MEMBER = "member";

  public RemoveMemberGsCommand() {
    super("removemember");
  }

  @Override
  public boolean mightHavePermission(Permissible permissible) {
    return true;
  }

  @Override
  protected boolean execute(CommandContext context, Grundstueck gs)
      throws InsufficientPermissionException {
    CommandSender sender = context.getSender();
    if (!sender.equals(gs.getOwner())) {
      MinecraftUtils.checkPermission(sender, getPermissionKey());
    }

    DefaultDomain members = gs.getRegion().getMembers();
    String memberName = context.get(MEMBER);
    members.removePlayer(memberName);
    String message =
        String.format("Der Spieler %s wurde erfolgreich vom Grundstück %s in der Welt %s entfernt.",
            memberName, gs.getName(), gs.getWorld().getName());
    MinecraftUtils.sendInfo(sender, message);
    return true;
  }

  @Override
  protected void addAdditionalParams(ParameterList pl) {
    pl.add(new Parameter(MEMBER));
  }

  @Override
  protected List<String> tabCompleteAdditionalParams(TabCompleteContext context) {
    String key = context.getKeyToComplete();
    if (MEMBER.equals(key)) {
      Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
      List<String> names = CollectionUtils.collect(onlinePlayers, new Closure<Player, String>() {
        @Override
        public String call(Player p) {
          return p.getName();
        }
      });
      return names;
    }
    return new ArrayList<String>(0);
  }

}
