package com.chosencraft.www.modreq.databases;

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
        //TODO : strip query of bad things
        // TODO: strip to alphanumerics plus basic simples
    }
}
