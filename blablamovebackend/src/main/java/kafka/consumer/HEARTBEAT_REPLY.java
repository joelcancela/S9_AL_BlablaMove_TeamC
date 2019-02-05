package kafka.consumer;

public class HEARTBEAT_REPLY {

    private String service_name;
    private Double timestamp;
    private Double request;

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public Double getRequest() {
        return request;
    }

    public void setRequest(Double request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "HEARTBEAT_REPLY{" +
                "service_name='" + service_name + '\'' +
                ", timestamp=" + timestamp +
                ", request=" + request +
                '}';
    }
}
