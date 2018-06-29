package com.chosencraft.www.modreq.databases.sql;

import com.chosencraft.www.modreq.ModReqMain;
import com.chosencraft.www.modreq.databases.Query;
import com.chosencraft.www.modreq.databases.sql.prepared.TableSetup;
import com.chosencraft.www.modreq.utils.Logger;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class RequestInterface
{
    private Logger log = ModReqMain.logger;

    private SQL sqlConnection;
    private Connection connection;


    public RequestInterface(Plugin plugin)
    {
        // Initialize Connection if not already connectioned
        this.sqlConnection = new SQL(plugin);
        if ( this.sqlConnection.connect() )
        {
            this.connection = sqlConnection.getConnection();
        }
        else {
            //TODO: What should I do here? unload? the plugin is useless without a connection
            // Unless we just kind of abuse the cached stuff for the time being?
        }


        // Initializes tables
        TableSetup tableSetup = new TableSetup(plugin, this);

    }

    /**
     * Executes a basic statement, DO NOT USE VARIABLES
     * @param query Query to be sent
     * @return The result set of the executed query, or null if it fails
     */
    public ResultSet executeStatement(Query query)
    {
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query.getQuery());
            statement.close();
            return results;

        }
        catch (SQLException sqlException)
        {
            log.logError("SQL Exception: " + sqlException.getErrorCode());
            log.logError("Query: " + query.toString());
            sqlException.printStackTrace();
            // If this fails as well, we actually don't care as it will fail for the same reason as the above one
            try { statement.close(); } catch (SQLException sqlException2){}
            return null;
        }
    }

    /**
     * Prepares and verifies the given query
     * Used this way to take
     * @param query Query to be sent
     * @return Return prepared query ready to be used.
     */
    public ResultSet executePreparedStatement(Query query, String... variables)
    {
        PreparedStatement preparedStatement = null;
        try
        {
             preparedStatement = connection.prepareStatement(query.getQuery());
            int i = 1;
            // set all the variables
            for (String variable : variables)
            {
                preparedStatement.setString( i++, variable);
            }
            ResultSet results = preparedStatement.executeQuery();
            preparedStatement.close();
            return results;
        }
        catch (SQLException sqlException)
        {
            log.logError("SQL Exception: " + sqlException.getErrorCode());
            log.logError("Query: " + query.toString());
            sqlException.printStackTrace();
            // If this fails as well, we actually don't care as it will fail for the same reason as the above one
            try { preparedStatement.close(); } catch (SQLException sqlException2){}
            return null;
        }
    }
}
