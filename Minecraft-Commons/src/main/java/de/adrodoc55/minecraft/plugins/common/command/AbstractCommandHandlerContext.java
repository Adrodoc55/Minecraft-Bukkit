package de.adrodoc55.minecraft.plugins.common.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommandHandlerContext {
  private final CommandSender sender;
  private final Command command;
  private final String alias;

  public AbstractCommandHandlerContext(CommandSender sender, Command command, String alias) {
    this.sender = sender;
    this.command = command;
    this.alias = alias;
  }

  public CommandSender getSender() {
    return sender;
  }

  public Command getCommand() {
    return command;
  }

  public String getLabel() {
    return alias;
  }

  public String getAlias() {
    return alias;
  }

}
