package me.shib.java.lib.botan;

public class BotanTrackResponse {

    private String status;
    private String info;

    protected BotanTrackResponse(String status, String info) {
        this.status = status;
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public boolean isAccepted() {
        return status.equalsIgnoreCase("accepted");
    }
}