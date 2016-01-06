/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.data;

import java.util.Date;

/**
 *
 * @author skuttik
 */
public interface SBXEvent {

    /* informative getter methods */
    public abstract String getType();

    public abstract String getName();

    public abstract String getDescription();

    /* geographic getter methods */
    public abstract boolean isGeoReferenced();

    public abstract SBXGeoPosition getPosition();

    public abstract long getRadius();

    /* temporal getter methods */
    public abstract Date getRefTime();

    public abstract Date getEndTime();

    public abstract int getDuration();
}
