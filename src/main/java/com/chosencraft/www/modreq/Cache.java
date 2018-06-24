package com.chosencraft.www.modreq;

import java.util.LinkedList;
import java.util.List;

public class Cache
{

    // Most of the time we can expect the cache to be under 5 objects
    // so iterating over them all is no big deal.  However,
    // we are adding and removing from it all the time.
    private LinkedList<ModReq> cache;

    private static Cache instance = new Cache();

    public static Cache getInstance()
    {
            return instance;
    }


    private Cache ()
    {
        this.cache =  new LinkedList<ModReq>();
    }

    /**
     * Adds a ModReq to the cache
     * @param modReq the ModReq to be added
     */
    public void addModReq(ModReq modReq)
    {
        cache.add(modReq);
    }

    /**
     * Remove a ModReq from the cache
     * @param modReq
     */
    public void removeModReq(ModReq modReq)
    {
        cache.remove(modReq);
    }

    /**
     * Return the full cache
     * @return the current local cache
     */
    public LinkedList<ModReq> getCache()
    {
        return this.cache;
    }

    /**
     * Updater that automatically saves the cache if needed
     */
    private void startUpdater()
    {

    }
}
