/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.test;

import java.util.Date;
import storyboardx.data.SBXGeoPosition;
import storyboardx.data.SBXEvent;

/**
 *
 * @author skuttik
 */
public class SBXEventImpl implements SBXEvent {

    private final Date refTime;
    private final int duration;
    private final String type;
    private final String name;

    public SBXEventImpl(Date refTime, int duration, String type, String name) {
        this.refTime = refTime;
        this.duration = duration;
        this.name = name;
        this.type = type;
    }

    @Override
    public Date getRefTime() {
        return refTime;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getDescription() {
        return "Long description for " + name + " of type" + type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isGeoReferenced() {
        return false;
    }

    @Override
    public SBXGeoPosition getPosition() {
        return null;
    }

    @Override
    public long getRadius() {
        return 0;
    }

    @Override
    public Date getEndTime() {
        return new Date(refTime.getTime() + duration);
    }

}
