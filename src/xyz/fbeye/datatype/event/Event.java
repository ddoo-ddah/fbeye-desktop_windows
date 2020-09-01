/*
 * Event.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package xyz.fbeye.datatype.event;

public class Event {
    public final Destination destination;
    public final EventDataType eventDataType;
    public final Object data;

    public Event(Destination destination, EventDataType eventDataType){
        this.destination = destination;
        this.eventDataType = eventDataType;
        data = null;
    }

    public Event(Destination destination, EventDataType eventDataType, Object data){
        this.destination = destination;
        this.eventDataType = eventDataType;
        this.data = data;
    }
}
