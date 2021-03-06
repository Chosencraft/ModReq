package com.chosencraft.www.modreq;

import com.chosencraft.www.modreq.utils.RequestState;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Object class to construct ModReqs
 */
public class ModReq
{

    /**  Request number of the ID*/
    private int     requestID;

    /** UUID of requester*/
    private  UUID    requesterUUID;
    /** Name of requester */
    private String  requester;

    /** UUID of the world where requester made the ModReq*/
    private UUID    locationWorldUUID;
    /** X coord of ModReq location*/
    private int     locationX;
    /** Y coord of ModReq location*/
    private int     locationY;
    /** Z coord of ModReq location*/
    private int     locationZ;
    /** Message of the ModReq*/
    private String  requestMessage;

    /** Request Owner UUID who took the task, null if none */
    private UUID    taskOwnerUUID;
    /** Request Owner name who took the task, null if none */
    private String  taskOwner;
    /** Request Owner who took the task, null if none */
    private String  taskResolution;
    /** State of the request, ENUM*/
    private RequestState state;
    /** Timestamp of the request*/
    private String timestamp;


    /**
     * Public constructor to create a new ModReq
     * @param requesterUUID UUID of the requester
     * @param worldUUID UUID of the world the requester is in
     * @param locationX X Coordinate of where the requester is
     * @param locationY Y Coordinate of where the requester is
     * @param locationZ Z Coordinate of where the requester is
     * @param requestMessage Message made by the requester
     */
    public ModReq(UUID requesterUUID, UUID worldUUID, int locationX, int locationY, int locationZ, String requestMessage)
    {
        this.requesterUUID = requesterUUID;

        this.locationWorldUUID = worldUUID;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationZ = locationZ;

        this.requestMessage = requestMessage;

        this.state = RequestState.UNCLAIMED;
        this.timestamp = new SimpleDateFormat("yyyy-mm-dd hh-mm:ss").format(Calendar.getInstance().getTime());
    }

    /**
     * Public constructor to recreate a ModReq from the database
     * @param requesterUUID UUID of the requester
     * @param worldUUID UUID of the world the requester is in
     * @param locationX X Coordinate of where the requester is
     * @param locationY Y Coordinate of where the requester is
     * @param locationZ Z Coordinate of where the requester is
     * @param requestMessage Message made by the requester
     */
    public ModReq(UUID requesterUUID, UUID worldUUID, int locationX, int locationY, int locationZ, String requestMessage,
    UUID taskOwnerUUID, String taskResolution, String timestamp, String requestState, int requestID)
    {
        this.requesterUUID = requesterUUID;

        this.locationWorldUUID = worldUUID;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationZ = locationZ;

        this.requestMessage = requestMessage;

        this.state = RequestState.UNCLAIMED;
        this.timestamp = new SimpleDateFormat("yyyy-mm-dd hh-mm:ss").format(Calendar.getInstance().getTime());

        this.taskOwnerUUID = taskOwnerUUID;
        this.taskOwner = indexName(this.taskOwnerUUID);
        this.taskResolution = taskResolution;
        this.timestamp = timestamp;
        this.state = parseRequestState(requestState);
        this.requestID = requestID;
    }


    /**
     * Returns the ID of the ModReq
     * @return ID of the ModReq
     */
    public int getID()
    {
        return this.requestID;
    }

    /**
     * Returns state of the ModReq
     * @return RequestState of ModReq
     */
    public RequestState getState()
    {
        return this.state;
    }

    /**
     * Get Requester UUID
     * @return UUID of the requester
     */
    public UUID getRequesterUUID()
    {
        return this.requesterUUID;
    }

    /**
     * Retrieve Name of the requester
     * @return Name of the requester
     */
    public String getRequester()
    {
        return  this.requester;
    }

    /**
     * Retrieves set location
     * @return Location where the request was made
     */
    public Location getRequesterLocation()
    {
        return new Location(Bukkit.getWorld(this.locationWorldUUID), this.locationX, this.locationY, this.locationZ);
    }


    /**
     * Retrieve task owner name
     * @return name of the task owner
     */
    public String getTaskOwner()
    {
        if (this.taskOwner == null && this.taskOwnerUUID != null)
        {
            this.taskOwner = indexName(this.taskOwnerUUID);
        }

        return this.taskOwner;
    }

    /**
     * Retrieve the UUID of the task owner
     * @return UUID of the task owner
     */
    public UUID getTaskOwnerUUID()
    {
        return taskOwnerUUID;
    }

    /**
     * Update the task owner
     * @param taskOwnerUUID UUID of the new task owner
     */
    public void setTaskOwnerUUID(UUID taskOwnerUUID)
    {
        if (this.state == RequestState.UNCLAIMED)
        {
            this.state = RequestState.CLAIMED;
        }

        this.taskOwnerUUID  = taskOwnerUUID;
        this.taskOwner = indexName(taskOwnerUUID);
    }


    /**
     * Return request message of the request
     * @return The request message
     */
    public String getRequestMessage()
    {
        return this.requestMessage;
    }

    /**
     * Retrieve the timestamp of the request
     * @return Timestamp of request at creation
     */
    public String getTimestamp()
    {
        return this.timestamp;
    }

    /**
     * Retrieve the resolution of the ModReq
     * @return The resolution of the ModReq
     */
    public String getTaskResolution()
    {
        return this.taskResolution;
    }

    /**
     * Complete the ModReq without a resolution message
     */
    public void completeTask()
    {
        this.state = RequestState.FINISHED;
        updateRequestInDatabase();
    }

    /**
     * Complete the ModReq with a resolution message
     * @param taskResolution Message of the task resolution
     */
    public void completeTask(String taskResolution)
    {
        this.state = RequestState.FINISHED;
        this.taskResolution = taskResolution;
        updateRequestInDatabase();
    }

    /**
     * Update the request to the backend databases
     */
    private void updateRequestInDatabase()
    {
        //TODO: manually update to SQL
    }

    private void addRequestToDatabase()
    {
        //TODO: add to database
        //TODO: also retrieve the task via all info specifications and get ID number
        //this.requestID = requestID() ?
    }

    /**
     * Parses the string request into a RequestState Enum
     * @param requestState state to be parsed
     * @return The Request state parsed
     */
    private RequestState parseRequestState(String requestState)
    {
        switch (requestState.toUpperCase())
        {
            case "FINISHED":
                return RequestState.FINISHED;
            case "CLAIMED":
                return RequestState.CLAIMED;
            case "UNCLAIMED":
                return RequestState.UNCLAIMED;
            default:
                // something happened to it, don't know what
                return RequestState.ORPHANED;
        }
    }

    private String indexName(UUID playerUUID)
    {
        //TODO: parse player uuid to name
        return null;
    }
}
