package me.shib.java.lib.botan;

import me.shib.java.lib.restiny.RESTinyClient;
import me.shib.java.lib.restiny.Response;
import me.shib.java.lib.restiny.requests.POST;
import me.shib.java.lib.restiny.util.JsonUtil;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Botan {

    private static final Logger logger = Logger.getLogger(Botan.class.getName());
    private static final String botanEndpoint = "https://api.botan.io";

    private String botanToken;
    private RESTinyClient resTinyClient;
    private JsonUtil jsonUtil;

    /**
     * Create an instance of this to track your bot and get awesome analytics.
     *
     * @param botanToken Obtain this token from @Botaniobot for your bot. Every bot will have a unique token.
     */
    public Botan(String botanToken) {
        this.botanToken = botanToken;
        this.resTinyClient = new RESTinyClient(botanEndpoint);
        this.jsonUtil = new JsonUtil();
    }

    /**
     * Tracks the user's data and categorizes it based on the given name as a context.
     *
     * @param user_id The user for whom the data is tracked for.
     * @param name    The name of the data which gives a context for analytics. For example, tracking a command could have the name as command.
     * @param data    The object or map representing the data. Could be a Message, Update or whatever.
     * @return Returns the tracking response object where the status can be verified if required.
     * @throws IOException thrown when there are service call failures.
     */
    public BotanTrackResponse track(long user_id, String name, Object data) throws IOException {
        logger.entering(this.getClass().getName(), "track", new Object[]{user_id, data, name});
        POST postRequest = new POST("track");
        postRequest.addParameter("token", botanToken);
        postRequest.addParameter("uid", user_id + "");
        postRequest.addParameter("name", name);
        postRequest.setPostObject(data);
        Response response;
        try {
            response = resTinyClient.call(postRequest);
        } catch (IOException e) {
            logger.throwing(this.getClass().getName(), "track", e);
            throw e;
        }
        String responseJson = response.getResponse();
        logger.log(Level.FINEST, "Service Response" + responseJson);
        BotanTrackResponse botanTrackResponse = null;
        if (responseJson != null) {
            try {
                botanTrackResponse = jsonUtil.fromJson(responseJson, BotanTrackResponse.class);
                if (botanTrackResponse.isAccepted()) {
                    logger.log(Level.FINEST, "Tracking Accepted");
                }
            } catch (Exception e) {
                logger.throwing(this.getClass().getName(), "track", e);
            }
        }
        logger.log(Level.FINEST, "Tracking failed by returning: " + responseJson);
        return botanTrackResponse;
    }

    /**
     * Tracks the user's data and categorizes it based on the given name as a context.
     *
     * @param user_id The user for whom the data is tracked for.
     * @param name    The name of the data which gives a context for analytics. For example, tracking a command could have the name as command.
     * @param data    The object or map representing the data. Could be a Message, Update or whatever.
     * @throws IOException thrown when there are service call failures.
     */
    public void trackNoSync(long user_id, String name, Object data) throws IOException {
        logger.entering(this.getClass().getName(), "trackNoResponse", new Object[]{user_id, data, name});
        POST postRequest = new POST("track");
        postRequest.addParameter("token", botanToken);
        postRequest.addParameter("uid", user_id + "");
        postRequest.addParameter("name", name);
        postRequest.setPostObject(data);
        resTinyClient.asyncCall(postRequest);
    }

    /**
     * Shortens a given URL and also tracks information about the user's location, device, user-agent, etc.
     *
     * @param user_id The user_id of the target user for whom the shortened link is intended for.
     * @param url     The URL that has to be shortened and made available for redirect.
     * @return Returns the shortened URL.
     * @throws IOException thrown when there are service call failures.
     */
    public String shortenURL(long user_id, String url) throws IOException {
        logger.entering(this.getClass().getName(), "shortenURL", new Object[]{user_id, url});
        POST postRequest = new POST("s/");
        postRequest.addParameter("token", botanToken);
        postRequest.addParameter("user_ids", user_id + "");
        postRequest.addParameter("url", url);
        Response response;
        try {
            response = resTinyClient.call(postRequest);
        } catch (IOException e) {
            logger.throwing(this.getClass().getName(), "shortenURL", e);
            throw e;
        }
        String responseText = response.getResponse();
        logger.log(Level.FINEST, "Service Response" + responseText);
        if ((responseText != null) && (responseText.toLowerCase().startsWith("http"))) {
            return responseText;
        }
        return null;
    }

}
