package com.chosencraft.www.modreq.databases.sql.prepared;

import com.chosencraft.www.modreq.ModReqMain;
import com.chosencraft.www.modreq.databases.Query;
import com.chosencraft.www.modreq.databases.sql.RequestInterface;
import com.chosencraft.www.modreq.utils.Logger;
import com.chosencraft.www.modreq.utils.RequestState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableSetup
{
    private Logger log = ModReqMain.logger;

    private RequestInterface request;
    private FileConfiguration config;
    String database;


    public TableSetup(Plugin plugin, RequestInterface requestInterface)
    {
        this.config = plugin.getConfig();
        this.request = ModReqMain.request;

        this.database = config.getString("database.mysql.database");
        String tablePrefix = config.getString("database.mysql.table_prefix");



        createRequestTable(tablePrefix);
        sendTestReq(tablePrefix);
    }

    private void createRequestTable(String tablePrefix)
    {
        // Check if table exists

        if (!checkIfTableExists(tablePrefix))
        {
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
                    "taskOwnerUUID CHAR(36)," +
                    "taskResolution TEXT(32767)," +
                    "timestamp DATETIME NOT NULL," +
                    "requestState ENUM('CLAIMED','UNCLAIMED','FINISHED',ORPHANED') NOT NULL" +
                    ");" );
        }
    }

    private void sendTestReq(String tablePrefix)
    {

    }

    private boolean checkIfTableExists(String tablePrefix)
    {
        Query query = new Query("SELECT count(*) FROM information_schema.TABLES " +
                "WHERE (TABLE_SCHEMA = '?') AND (TABLE_NAME = '?');" );
        ResultSet results = ModReqMain.request.executePreparedStatement(query, database, tablePrefix +"_requestTable");
        try
        {
            if ( results == null || results.wasNull())
            {
                return false;
            }
        }
        catch (SQLException sqlException)
        {
            // Couldn't connect so the server, time to debug
            log.logError("Could not connect to MySQL Database! " + sqlException.getErrorCode());
            sqlException.printStackTrace();
        }
        return true;
    }
}
