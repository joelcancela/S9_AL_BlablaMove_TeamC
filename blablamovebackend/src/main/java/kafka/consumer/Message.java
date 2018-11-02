package kafka.consumer;

public class Message {
    private String action;
    private Object message;

    public Message() {
    }

    public Message(String action, Object message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "action='" + action + '\'' +
                ", message=" + message +
                '}';
    }
}
