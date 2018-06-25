package com.chosencraft.www.modreq.databases.sql.prepared;

import com.chosencraft.www.modreq.databases.Query;
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
        Query requestTable = new Query("" +
                "CREATE TABLE " + tablePrefix + "_requestTable " +
                "(" +
                "requestID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "requesterUUID CHAR(36) NOT NULL," +
                "worldUUID CHAR(36) NOT NULL," +
                "xLocation INT NOT NULL," +
                "yLocation INT NOT NULL," +
                "zLocation INT NOT NULL," +
                "requestMessage TEXT(32767) NOT NULL," +
                "taskOwner CHAR(36)," +
                "taskResolution TEXT(32767)," +
                "timestamp DATETIME NOT NULL," +
                "requestState ENUM('CLAIMED','UNCLAIMED','FINISHED',ORPHANED') NOT NULL," +
                ")");

    }

}
