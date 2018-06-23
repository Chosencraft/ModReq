package com.chosencraft.www.modreq.databases.sql;

import com.chosencraft.www.modreq.databases.sql.prepared.TableSetup;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class RequestInterface
{

    private SQL sqlConnection;


    public RequestInterface(Plugin plugin)
    {
        // Initialize Connection
        this.sqlConnection = new SQL(plugin);
        this.sqlConnection.connect();

        // Initializes tables
        TableSetup tableSetup = new TableSetup(plugin, this);

    }

    public boolean executeStatement(Statement statement)
    {
        //TODO: execute statement
        return false;
    }

    public boolean executePreparedStatement(PreparedStatement preparedStatement)
    {
        //TODO: Execute prepared statement
        return false;
    }
}
