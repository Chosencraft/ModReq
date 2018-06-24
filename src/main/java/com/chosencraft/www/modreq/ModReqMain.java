package com.chosencraft.www.modreq;

import com.chosencraft.www.modreq.databases.sql.RequestInterface;
import com.chosencraft.www.modreq.listeners.NotifyStaffOnLogin;
import com.chosencraft.www.modreq.tasks.AutoNotifyStaff;
import com.chosencraft.www.modreq.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ModReqMain extends JavaPlugin
{

    public static Logger logger;

    public void onEnable()
    {
        this.logger = new Logger(this.getLogger());

        connectToDatabase();
        // start calls
        registerListeners();
        registerCommands();
        startNotifier();
    }

    public void onDisable()
    {

    }


    /**
     * Connects the plugin to the database
     */
    private void connectToDatabase()
    {
        new RequestInterface(this);

    }

    /**
     * Registers all listeners for the plugin
     */
    private void registerListeners()
    {
        PluginManager manager = Bukkit.getServer().getPluginManager();

        manager.registerEvents(new NotifyStaffOnLogin(), this);
    }

    /**
     * Registers all commands for the plugin
     */
    private void registerCommands()
    {

    }

    /**
     * Start the administrative notifier
     */
    private void startNotifier()
    {
        // notify every 5 minutes, 30 second startup
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AutoNotifyStaff(), 30, 300);
    }
}
