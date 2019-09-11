package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Request hold information about basic request data.
 */
public class Request {
    private String requestId;
    private int type;

    protected Request(String requestId, int type) {
        this.requestId = requestId;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return type == request.type &&
                Objects.equals(requestId, request.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, type);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", type=" + type +
                '}';
    }
}
