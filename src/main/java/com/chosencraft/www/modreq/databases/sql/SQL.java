package com.chosencraft.www.modreq.databases.sql;

import com.chosencraft.www.modreq.ModReqMain;
import com.chosencraft.www.modreq.databases.Database;
import com.chosencraft.www.modreq.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL extends Database
{

    private Logger log = ModReqMain.logger;

    private String username;
    private String password;
    private String connectionURL;

    private Connection connection;

    public static String database;

    /**
     * MySQL Wrapper
     *
     * @param plugin This plugin
     */
    public SQL(Plugin plugin) {

        // grab config
        FileConfiguration config = plugin.getConfig();

        // set params
        this.username = config.getString("database.mysql.username");
        this.password = config.getString("database.mysql.password");
        this.database = config.getString("database.mysql.database");
        String host =  config.getString("database.mysql.host");
        int port =  config.getInt("database.mysql.port");
        config.getValues(false);

        // Added end string to go over HTTP instead of HTTPS because I was having issues setting up SSL for mySQL (website works fine though)
        // Also auto reconnect flag
        connectionURL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";
    }

    /**
     * Attempts to creates a connection to a MySQL Server
     *
     * @return true if connected or false if it was unable to establish a
     *         connection
     */
    @Override
    public boolean connect() {

        try {
            // Presume nothing is ever done right and grab the driver class ourselves
            Class.forName("com.mysql.jdbc.Driver");
            // Creates the connection, then returns the instance of it
            this.connection = DriverManager.getConnection(connectionURL, username, password);
            return isConnected();

        }
        catch (ClassNotFoundException noClassException) {
            // No SQL driver is found, abort the mission
            log.logError("JDBC Driver not found! " + noClassException.getCause());
            noClassException.printStackTrace();
        }
        catch (SQLException sqlException) {
            // Couldn't connect so the server, time to debug
            log.logError("Could not connect to MySQL Database! " + sqlException.getErrorCode());
            sqlException.printStackTrace();
        }
        // Find why it could not connection
        return false;
    }

    /**
     * Attempts to retrieve an instance of the connection
     *
     * @return an instance of the connection, returns false if none can be found
     */
    @Override
    public Connection getConnection() {
        // Simple getter
        return connection;
    }

}
