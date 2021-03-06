package com.endicott.edu.datalayer;

import com.endicott.edu.models.SportModel;
import com.endicott.edu.models.SportModel;
import com.endicott.edu.ui.UiMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

public class SportsSimTalker {
    private static Logger logger = Logger.getLogger("SportsSimTalker");

    public static boolean addSport(String runId, String server, String sportName) {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(server + "sports/" + runId + "/" + sportName);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        String json = "{  \"runID\" : \"" + runId + "\"," +
                "    \"sportName\" : \"" + sportName + "\"" + "}";

        logger.info("Creating sport: " + json);

        Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
        String responseAsString = response.readEntity(String.class);

        if (response.getStatus() != 200) {
            logger.severe("Add sport: Got a bad response: " + response.getStatus());
            return false;
        }
        else {
            logger.info("Add sport: Got a ok response: " + runId);
            return true;
        }
    }
    public static void deleteSport(String server, String runId, String sportName){
        logger.info("attempting to delete sport.");
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(server + "sports/" + runId + "/" + sportName);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);

        Response response = invocationBuilder.delete();
        String responseAsString = response.readEntity(String.class);

        return;



    }
    static public  SportModel[] getSports(String server, String runId, UiMessage msg){
        SportModel[] sport;
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(server + "sports/" + runId);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();
        String responseAsString = response.readEntity(String.class);
        Gson gson = new GsonBuilder().create();
        logger.info("Sports as string: " +responseAsString);

        try {
            sport = gson.fromJson(responseAsString, SportModel[].class);
        } catch (Exception e) {
            msg.setMessage("Sports Failure:" + e.getMessage());
            return null;
        }
        return sport;
    }
    static public SportModel[] getAvailableSports(String server, String runId, UiMessage msg){
        logger.info("Inside availableSports ");
        SportModel[] sport;
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(server + "sports/" + runId + "/available");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();
        String responseAsString = response.readEntity(String.class);
        Gson gson = new GsonBuilder().create();
        logger.info("Available sports as string: " +responseAsString);

        try {
            sport = gson.fromJson(responseAsString, SportModel[].class);
        } catch (Exception e) {
            msg.setMessage("Sports Failure:" + e.getMessage());
            return null;
        }
        return sport;
    }
}
