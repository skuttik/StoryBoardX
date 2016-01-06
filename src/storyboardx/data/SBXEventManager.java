/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author skuttik
 */
public interface SBXEventManager {

    public abstract String getType();

    public abstract String getDescription();

    public abstract boolean hasGeoReferencedEvents();

    public abstract boolean canManageGeoFilter();

    public abstract ArrayList<SBXEvent> getEvents();

    public abstract ArrayList<SBXEvent> getFilteredEvents(Date startDate, Date endDate, ArrayList<SBXFilterArea> geoFilter, String customFilter);

    public void addEventListener(SBXEventListener listener);

    public void removeEventListener(SBXEventListener listener);

}
