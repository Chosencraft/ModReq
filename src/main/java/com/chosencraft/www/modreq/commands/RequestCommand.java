package com.chosencraft.www.modreq.commands;

import com.chosencraft.www.modreq.ModReqMain;
import com.chosencraft.www.modreq.databases.Query;
import com.chosencraft.www.modreq.utils.Logger;
import com.chosencraft.www.modreq.utils.RequestState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestCommand implements CommandExecutor
{

    Logger log = ModReqMain.logger;

    String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA +"ModReq" + ChatColor.GOLD + "]" + ChatColor.RED ;
    String antiUTF8Strip = "[^\\u0000-\\u007F]+";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (!(commandSender instanceof Player))
        {
            log.logInfo( prefix+ "Sorry mate, can't make a modreq from the console!");
            return true;
        }
        Player player = (Player) commandSender;

        if (args.length == 0)
        {
            player.sendMessage(prefix + "Please add a message to your request!");
            player.sendMessage(prefix + "/req <message>");
            return true;
        }
        StringBuilder builder = new StringBuilder();
        for (String arg : args)
        {
            builder.append(arg);
        }

        sendToDatabase(player, builder.toString());
        player.sendMessage(prefix + "Request sent!");

        return true;
    }

    private void sendToDatabase(Player player, String message)
    {
        Query query = new Query("" +
                "INSERT INTO " +
                "?" + // table
                "(" +
                "requesterUUID," +// requester uuid
                "worldUUID," + // worldUUID
                "xLocation," + // xLoc
                "yLocation," + // yLoc
                "zLocation," + // zLoc
                "requestMessage," + // message
                "timestamp," +
                "requestState"+ //
                ")" +
                "VALUES" +
                "(" +
                "?," + // requesterUUID
                "?," + // world uuid
                "?," + // xloc
                "?," +// y loc
                "?," +// z loc
                "?," +  // message
                "?," + // timestamp
                "?"  + // request State
                ")" +
                ";");
        message = message.replaceAll(antiUTF8Strip, "");
        String timestamp = new SimpleDateFormat("yyyy-mm-dd HH-mm-ss").format(new Date());
        ResultSet results = ModReqMain.request.executePreparedStatement(query, player.getUniqueId().toString(),
                player.getWorld().getUID().toString(), String.valueOf(player.getLocation().getBlockX()), String.valueOf(player.getLocation().getBlockY()),
                String.valueOf(player.getLocation().getBlockZ()), message, timestamp, RequestState.UNCLAIMED.toString());
        //TODO: maybe do something with the results
    }
}
