package Server;

import java.util.Map;

/**
 * The `DataEvent` class represents an event containing data received from the server.
 * It encapsulates information such as the event source, data associated with the event,
 * and the type of the event (represented by the `ServerEvents` enum).
 */
public class DataEvent {

    private Object source;
    private Map<String, String> data;
    private ServerEvents eventType;

    /**
     * Constructs a `DataEvent` object with the specified parameters.
     *
     * @param source    The source object that triggered the event.
     * @param data      A map containing data associated with the event.
     * @param eventType The type of the event as represented by `ServerEvents` enum.
     */
    public DataEvent(Object source, Map<String, String> data, ServerEvents eventType) {
        this.source = source;
        this.data = data;
        this.eventType = eventType;
    }

    /**
     * Gets the source object that triggered the event.
     *
     * @return The source object.
     */
    public Object getSource() {
        return source;
    }

    /**
     * Gets the data associated with the event.
     *
     * @return A map containing event data.
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * Gets the type of the event represented by the `ServerEvents` enum.
     *
     * @return The type of the event.
     */
    public ServerEvents getEventType() {
        return eventType;
    }
}
