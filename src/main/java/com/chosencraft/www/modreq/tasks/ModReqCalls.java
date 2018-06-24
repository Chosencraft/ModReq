package com.chosencraft.www.modreq.tasks;

import com.chosencraft.www.modreq.Cache;
import com.chosencraft.www.modreq.ModReq;
import com.chosencraft.www.modreq.utils.RequestState;

import java.util.LinkedList;

public class ModReqCalls
{


    /**
     * Get all the modreqs marked as unclaimed
     * @return A list of unclaimed ModReqs
     */
    public static LinkedList<ModReq> getUnclaimedModReqs()
    {
        LinkedList<ModReq> list = new LinkedList<ModReq>();
        LinkedList<ModReq> cache = Cache.getInstance().getCache();

        if (cache != null && cache.size() != 0)
        {
            for (ModReq req : cache)
            {
                if (req.getState().equals(RequestState.UNCLAIMED))
                {
                    list.add(req);
                }
            }
        }

        return list;
    }


    /**
     * Get all the modreqs marked as claimed
     * @return A list of claimed ModReqs
     */
    public static LinkedList<ModReq> getClaimedModReqs()
    {
        LinkedList<ModReq> list = new LinkedList<ModReq>();
        LinkedList<ModReq> cache = Cache.getInstance().getCache();

        if (cache != null && cache.size() != 0)
        {
            for (ModReq req : cache)
            {
                if (req.getState().equals(RequestState.CLAIMED))
                {
                    list.add(req);
                }
            }
        }

        return list;
    }

    /**
     * Get all the modreqs that should be notified of with
     * @return A list of unclaimed ModReqs
     */
    public static LinkedList<ModReq> getNotifiableModReqs()
    {
        LinkedList<ModReq> list = new LinkedList<ModReq>();
        LinkedList<ModReq> cache = Cache.getInstance().getCache();

        if (cache != null && cache.size() != 0)
        {
            for (ModReq req : cache)
            {
                if (!(req.getState().equals(RequestState.FINISHED)))
                {
                    list.add(req);
                }
            }
        }

        return list;
    }
}
