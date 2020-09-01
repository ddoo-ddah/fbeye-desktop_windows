/*
 * EventList.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package xyz.fbeye.datatype.event;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    private List<Event> list;

    public EventList(){
        list = new ArrayList<>();
    }

    public void add(Event e){
        list.add(e);
    }

    public void remove(int index){
        list.remove(index);
    }

    public Event get(int index){
        return list.get(index);
    }

    public int size(){
        return list.size();
    }
}
