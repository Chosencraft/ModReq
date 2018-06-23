package com.chosencraft.www.modreq;

import com.chosencraft.www.modreq.databases.sql.RequestInterface;
import com.chosencraft.www.modreq.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class ModReqMain extends JavaPlugin
{

    public static Logger logger;

    public void onEnable()
    {
        this.logger = new Logger(this.getLogger());

        connectToDatabase();

    }

    public void onDisable()
    {

    }


    private void connectToDatabase()
    {
        new RequestInterface(this);

    }

}
