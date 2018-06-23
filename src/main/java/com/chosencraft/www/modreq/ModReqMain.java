package com.chosencraft.www.modreq;

import com.chosencraft.www.modreq.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class ModReqMain extends JavaPlugin
{

    public static Logger logger;

    public void onEnable()
    {
        this.logger = new Logger(this.getLogger());


    }

    public void onDisable()
    {

    }



}
