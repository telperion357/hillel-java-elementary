package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Error response holds basic information about response such as request id and error code.
 */
public class ErrorResponse {
    private Integer status;
    private String requestId;
    private Integer type;

    public ErrorResponse(String requestId, Integer type) {
        this.requestId = requestId;
        this.type = type;
    }

    public ErrorResponse(Integer status, String requestId, Integer type) {
        this.status = status;
        this.requestId = requestId;
        this.type = type;
    }

    public boolean isError() {
        return status != null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getRequestId() {
        return requestId;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(requestId, that.requestId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, requestId, type);
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", requestId='" + requestId + '\'' +
                ", type=" + type +
                '}';
    }
}
