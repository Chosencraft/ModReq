package com.chosencraft.www.modreq.commands;

import com.chosencraft.www.modreq.Cache;
import com.chosencraft.www.modreq.ModReq;
import com.chosencraft.www.modreq.ModReqMain;
import com.chosencraft.www.modreq.databases.Query;
import com.chosencraft.www.modreq.databases.sql.RequestInterface;
import com.chosencraft.www.modreq.databases.sql.SQL;
import com.chosencraft.www.modreq.utils.Logger;
import com.chosencraft.www.modreq.utils.RequestState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModReqCommand implements CommandExecutor
{

    private Logger log = ModReqMain.logger;
    private String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA +"ModReq" + ChatColor.GOLD + "] " + ChatColor.RED ;
    private  String specifyID = prefix + "Please specify a ModReq ID!";

    private Cache cache = Cache.getInstance();

    private  RequestInterface request = ModReqMain.request;

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
                claimReq(player,args);
                break;
            case "unclaim":
                unclaimReq(player,args);
                break;
            case "reopen":

                break;
            case "info":

                break;
            case "list":

            case "teleport":

                break;
            case "tp":
                teleportToReq(player, args);
                break;

                default:
                    printHelpMenu(player);
                    break;
        }

        return true;
    }


    private void teleportToReq(Player player, String[] args)
    {
        if (args.length < 2)
        {
            player.sendMessage(specifyID);
            return;
        }
        else
        {
            int taskID = parseTaskID(args[1]);
            if (taskID != -1)
            {
                Query query = new Query("SELECT xLocation,yLocation,zLocation,worldUUID FROM ? WHERE requestID=?");
                ResultSet results = request.executePreparedStatement(query, SQL.tableName, String.valueOf(taskID));

                while

                Location location = new Location(Bukkit.getWorld(worldUUID), xLoc, yLoc, zLoc);
                player.teleport(location);
                player.sendMessage(prefix + "Teleported to task " + taskID);
            }
            else
            {
                player.sendMessage(prefix + "That is not a valid taskID!");
            }

        }
    }

    /**
     * Claim a modreq
     * @param player Player claiming the modreq
     * @param args
     */
    private void claimReq(Player player, String[] args)
    {
        if (args.length < 2)
        {
            player.sendMessage(specifyID);
            return;
        }
        else
        {
            int taskID = parseTaskID(args[1]);
            if (taskID != -1)
            {
                Query query = new Query("UPDATE " +
                        SQL.tableName +
                        "SET taskOwnerUUID=?, state=? " +
                        "WHERE requestID=?" +
                        ")"
                        );
                ResultSet results = request.executePreparedStatement(query, player.getUniqueId().toString(), RequestState.CLAIMED.toString(), String.valueOf(taskID));
                int rows = 0;
                try
                {
                    while (results.next())
                    {
                        rows++;
                    }
                }
                catch (SQLException sqlException)
                {
                    player.sendMessage(prefix + "Your ModReq was not found!");
                    return;
                }
                if (rows != 1)
                {
                    player.sendMessage(prefix + "Your ModReq was not found!");
                }
                else
                {
                    player.sendMessage("You have claimed task " + taskID);
                    cache.updateCache();
                    return;
                }

            }
            else
            {
                player.sendMessage(prefix + "That is not a valid taskID!");
            }
        }
    }


    /**
     * Unclaim a modreq
     * @param player Player unclaiming the modreq
     * @param args arg pass through
     */
    private void unclaimReq(Player player, String[] args)
    {
        if (args.length < 2)
        {
            player.sendMessage(specifyID);
            return;
        }
        else
        {
            int taskID = parseTaskID(args[1]);
            if (taskID != -1)
            {
                Query query = new Query("UPDATE " +
                        SQL.tableName +
                        "SET taskOwnerUUID=?, state=? " +
                        "WHERE requestID=?" +
                        ")"
                );
                ResultSet results = request.executePreparedStatement(query, null, RequestState.UNCLAIMED.toString(), null);
                int rows = 0;
                try
                {
                    while (results.next())
                    {
                        rows++;
                    }
                }
                catch (SQLException sqlException)
                {
                    player.sendMessage(prefix + "Your ModReq was not found!");
                    return;
                }
                if (rows != 1)
                {
                    player.sendMessage(prefix + "Your ModReq was not found!");
                }
                else
                {
                    player.sendMessage("You have unclaimed task " + taskID);
                    cache.updateCache();
                    return;
                }

            }
            else
            {
                player.sendMessage(prefix + "That is not a valid taskID!");
            }
        }
    }


    /**
     * Prints the help menu
     * @param sender Player to send the help menu to
     */
    private void printHelpMenu(Player sender)
    {
        sender.sendMessage(prefix + "=========================================");
        sender.sendMessage(formatCmd("help","Displays this help menu"));
        sender.sendMessage(formatCmd("claim <id>","Take ownership of the modreq"));
        sender.sendMessage(formatCmd("unclaim <id>","Stop taking ownership of the modreq"));
        sender.sendMessage(formatCmd("reopen <id>", "Opens a previously closed modreq"));
        sender.sendMessage(formatCmd("info <id>", "Displays information about the modreq"));
        sender.sendMessage(formatCmd("claim <id>", "Claims the modreqt"));
        sender.sendMessage(formatCmd("list", "Lists the currently open modreqs"));
        sender.sendMessage(formatCmd("teleport", "Teleport to the place where the modreq was sent from!"));
    }

    /**
     * Easily color formats command message
     * @param command Command to be formatted
     * @param message Message to be formatted
     * @return A decently formatted message
     */
    private String formatCmd(String command, String message)
    {
        return ChatColor.GOLD + command + ChatColor.DARK_RED + " : " + ChatColor.GREEN + message;
    }

    /**
     * Parse the given task ID
     * @param taskIDString taskID to be parsed
     * @return taskID or -1 if not a valid taskID
     */
    private int parseTaskID(String taskIDString)
    {
        int taskID;
        try
        {
            taskID = Integer.parseInt(taskIDString);
        }
        catch (NumberFormatException numberException)
        {
            // all ModReq IDs will be positive
            return -1;
        }
        return taskID;
    }
}
