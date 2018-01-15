package com.javier.cardnote.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javiergonzalezcabezas on 15/1/18.
 */

public class CreateEvents {
    public static List<Event> getEvents(List<Example> list, String noFound) {
        List<Event> returnEvents = new ArrayList<>();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {

                Event event = new Event(noFound, noFound, "");

                if (list.get(i).getTitle() != null) {
                    event.setTitle(list.get(i).getTitle());
                }

                if (list.get(i).getDescription() != null) {
                    event.setDescription(list.get(i).getDescription());
                }

                if (list.get(i).getImage() != null) {
                    event.setImage(list.get(i).getImage());
                }

                returnEvents.add(event);
            }
        }

        return returnEvents;
    }
}
