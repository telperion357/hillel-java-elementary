package ua.hillel.java.elementary1.chat.common.model;

public class HiRequest extends Request {
    private String username;

    public HiRequest(String requestId, String username) {
        super(requestId, EventType.HI_REQUEST.getType());
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "HiRequest{" +
                "username='" + username + '\'' +
                "} " + super.toString();
    }
}
