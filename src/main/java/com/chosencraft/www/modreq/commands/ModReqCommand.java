package com.chosencraft.www.modreq.commands;

import com.chosencraft.www.modreq.ModReqMain;
import com.chosencraft.www.modreq.utils.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

public class ModReqCommand implements CommandExecutor
{

    Logger log = ModReqMain.logger;
    String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA +"ModReq" + ChatColor.GOLD + "]" + ChatColor.RED ;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {

        if (!(commandSender instanceof Player))
        {
            log.logInfo(prefix + "This command is only usable in game!");
            return true;
        }
        Player player = (Player) commandSender;

        if (args.length == 0)
        {
            printHelpMenu(player);
            return true;
        }

        switch (args[0])
        {
            case "help" :
                printHelpMenu(player);
                break;
            case "claim":

                break;
            case "unclaim":

                break;
            case "reopen":

                break;
            case "info":

                break;

                default:
                    printHelpMenu(player);
                    break;
        }

        return true;
    }

    private void printHelpMenu(Player sender)
    {
        sender.sendMessage(prefix + "=========================================");
    }
}
