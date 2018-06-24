package com.chosencraft.www.modreq.listeners;

import com.chosencraft.www.modreq.Permissions;
import com.chosencraft.www.modreq.tasks.AutoNotifyStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class NotifyStaffOnLogin implements Listener
{

    @EventHandler (priority = EventPriority.NORMAL)
    public void onStaffLogin(PlayerLoginEvent loginEvent)
    {
        Player player = loginEvent.getPlayer();
        if (player.hasPermission(Permissions.PERM_UNCLAIMED_NOTIFY))
        {
            AutoNotifyStaff.executeSingleNotifier(player);
        }
    }
}
