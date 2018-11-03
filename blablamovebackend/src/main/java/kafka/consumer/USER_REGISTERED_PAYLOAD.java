package kafka.consumer;

public class USER_REGISTERED_PAYLOAD {
    private Integer request;

    public USER_REGISTERED_PAYLOAD() {
    }

    public USER_REGISTERED_PAYLOAD(Integer request) {
        this.request = request;
    }

    public Integer getRequest() {
        return request;
    }

    public void setRequest(Integer request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "USER_REGISTERED_PAYLOAD{" +
                "request=" + request +
                '}';
    }
}
