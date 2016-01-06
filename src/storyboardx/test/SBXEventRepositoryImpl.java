/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import storyboardx.data.SBXEvent;
import storyboardx.data.SBXEventListener;
import storyboardx.data.SBXEventManager;
import storyboardx.data.SBXFilterArea;

/**
 *
 * @author skuttik
 */
public class SBXEventRepositoryImpl implements SBXEventManager {

    private ArrayList<SBXEvent> db;
    private ArrayList<SBXEventListener> listeners;
    private final String type;

    public SBXEventRepositoryImpl(String type) {
        db = new ArrayList<>();
        listeners = new ArrayList<>();
        this.type = type;
    }

    @Override
    public ArrayList<SBXEvent> getEvents() {
        ArrayList<SBXEvent> out = new ArrayList<>();
        db.stream().forEach(ev -> {
            out.add(ev);
        });
        return out;
    }

    public void addEvent(String description, Long start, int duration) {
        SBXEventImpl newEvent = new SBXEventImpl(new Date(start), duration, type, description);
        db.add(newEvent);
        listeners.stream().forEach(listner -> {
            listner.onNewEvent(newEvent);
        });
    }

    @Override
    public void addEventListener(SBXEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(SBXEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return "Description of events of type " + type;
    }

    @Override
    public boolean hasGeoReferencedEvents() {
        return false;
    }

    @Override
    public boolean canManageGeoFilter() {
        return false;
    }

    @Override
    public ArrayList<SBXEvent> getFilteredEvents(Date startDate, Date endDate, ArrayList<SBXFilterArea> geoFilter, String customFilter) {
        ArrayList<SBXEvent> outList = new ArrayList<>();
        long duration = endDate.getTime() - startDate.getTime();
        db.stream()
                .filter((ev) -> (ev.getRefTime().getTime() <= (endDate.getTime())
                        && (ev.getRefTime().getTime() + duration) >= startDate.getTime()))
                .forEach((ev) -> {
                    outList.add(ev);
                });
        return outList;
    }
}
