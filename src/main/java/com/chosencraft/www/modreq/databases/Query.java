package com.chosencraft.www.modreq.databases;

import java.util.regex.Pattern;

/**
 * Class used to forcefully pass in queries and strip
 * them of potentially downfall results
 */
public class Query
{
    private String query;

    /**
     * Query Constructor
     * @param query Query that should be executed
     */
    public Query (String query)
    {
        this.query = query;
        stripQuery();
    }


    /**
     * Returns stripped query
     * @return The stripped query
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * Parses query
     */
    private void stripQuery()
    {
        // word characters , space, @, and hyphens
        String regex = "[^\\w\\s\\.@-]";

        // strip with regex pattern
        this.query = this.query.replaceAll(regex, "");

    }
}
