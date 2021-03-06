package de.adrodoc55.minecraft.plugins.common.utils;

import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class MinecraftUtils {

  public static void setXp(Player player, double xp) {
    int level = (int) xp;
    float exp = (float) (xp - (double) level);
    player.setLevel(level);
    player.setExp(exp);
  }

  public static Location getCenteredBlockLocation(Block block) {
    Location location = block.getLocation().add(0.5f, 0.5f, 0.5f);
    return location;
  }

  public static Location getCenteredBlockLocation(Block block, BlockFace blockFace) {
    Location location = getCenteredBlockLocation(block);
    double modX = ((double) blockFace.getModX()) / 2;
    double modY = ((double) blockFace.getModY()) / 2;
    double modZ = ((double) blockFace.getModZ()) / 2;
    Location modifier = new Location(block.getWorld(), modX, modY, modZ);
    location.add(modifier);
    return location;
  }

  private static Cache<CommandSender, String> MESSAGES = CacheBuilder.newBuilder()//
      .concurrencyLevel(1)//
      .expireAfterWrite(9, TimeUnit.SECONDS)//
      .build();

  /**
   * Sendet eine Nachricht an den Spieler, falls in den letzten 9 Sekunden nicht die selbe Nachricht
   * bereits an diesen Spieler geschickt wurde.
   *
   * Diese Methode tut nichts, wenn eines der Argumente null ist.
   *
   * @param commandSender
   * @param message
   */
  public static void sendMessage(final CommandSender commandSender, String message) {
    if (commandSender == null || message == null) {
      return;
    }
    String lastMessage = MESSAGES.getIfPresent(commandSender);
    if (message.equals(lastMessage)) {
      return;
    }
    MESSAGES.put(commandSender, message);
    commandSender.sendMessage(message);
  }

  public static void sendInfo(final CommandSender commandSender, String message) {
    sendMessage(commandSender, ChatColor.YELLOW + message);
  }

  public static void sendError(final CommandSender commandSender, String message) {
    sendMessage(commandSender, ChatColor.RED + message);
  }

  /**
   * Throws an {@link InsufficientPermissionException} if {@code permissible} does not have the
   * permission {@code permission}.
   *
   * @param permissible the {@link Permissible} to check
   * @param permission the permission to check for
   * @throws InsufficientPermissionException if {@code permissible} does not have the
   *         {@code permission}
   */
  public static void checkPermission(Permissible permissible, String permission)
      throws InsufficientPermissionException {
    if (!permissible.hasPermission(permission)) {
      throw new InsufficientPermissionException(permission);
    }
  }

}
