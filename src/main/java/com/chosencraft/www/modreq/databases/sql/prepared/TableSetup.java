package com.chosencraft.www.modreq.databases.sql.prepared;

import com.chosencraft.www.modreq.databases.sql.RequestInterface;
import org.bukkit.plugin.Plugin;

import java.sql.DatabaseMetaData;

public class TableSetup
{
    private RequestInterface request;

    public TableSetup(Plugin plugin, RequestInterface requestInterface)
    {
        String tablePrefix = plugin.getConfig().getString("database.mysql.table_prefix");

        this.request = requestInterface;

        createRequestTable(tablePrefix);

    }

    private void createRequestTable(String tablePrefix)
    {
        // Check if table exists
        DatabaseMetaData tables;

        //TODO: create request table
    }

}
