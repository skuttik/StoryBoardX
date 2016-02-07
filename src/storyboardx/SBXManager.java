/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import storyboardx.data.SBXEvent;
import storyboardx.data.SBXEventManager;
import storyboardx.ui.SBXEventViewer;
import storyboardx.ui.SBXShotPreview;
import storyboardx.ui.SBXTimeBar;

/**
 *
 * @author skuttik
 */
public class SBXManager {

    private static SBXManager instance = null;
    private SBXEventViewer viewer = null;
    private SBXTimeBar timeBar = null;
    private SBXShotPreview shotPreview = null;
    private Date startDate;
    private long totalDuration;
    private final HashMap<String, SBXEventManager> managerMap;
    private Rectangle shotPreviewRect;

    private SBXManager() {
        managerMap = new HashMap<>();
    }

    public static SBXManager getInstance() {
        if (instance == null) {
            instance = new SBXManager();
        }
        return instance;
    }

    /*
     Shot preview management
     */
    public void setShotPreview(SBXShotPreview shotPreview) {
       this.shotPreview = shotPreview;
    }

    public void showShotPreview(ImagePattern imagePattern, double positionX, double positionY) {
        shotPreview.show(imagePattern, positionX, positionY);
    }

    public void movePreview(double positionX, double positionY) {
        shotPreview.moveTo(positionX, positionY);
    }
    
    public void hideShotPreview() {
        shotPreview.hide();
    }
    /*
     Event Viewer management
     */

    public void setViewer(SBXEventViewer viewer) {
        this.viewer = viewer;
    }

    public SBXEventViewer getViewer() {
        return viewer;
    }

    public void clearEventInfo() {
        if (viewer != null) {
            viewer.clearEventInfo();
        }
    }

    public void displayEventInfo(SBXEvent event) {
        if (viewer != null) {
            viewer.displayEventInfo(event);
        }
    }

    /*
     Time bar management
     */
    public void setTimeBar(SBXTimeBar timeBar) {
        this.timeBar = timeBar;
    }

    public void setTimeWindow(Date startDate, long totalDuration) {
        if (timeBar != null) {
            timeBar.set(startDate, totalDuration);
        }
        this.startDate = startDate;
        this.totalDuration = totalDuration;
    }

    public void setCurrentPointer(double pointerX) {
        if (timeBar != null) {
            if (pointerX < 0.0) {
                timeBar.clearCurrentDate();
            } else {
                timeBar.updateCurrentDate(startDate.getTime(), totalDuration, pointerX);
            }
        }
    }

    /*
     Event Managers management
     */
    public void addEventManager(String eventType, SBXEventManager manager) {
        managerMap.put(eventType, manager);
    }

    public void removeEventManager(String eventType) {
        managerMap.remove(eventType);
    }

    public Set<String> getEventTypes() {
        return managerMap.keySet();
    }

    public SBXEventManager getEventManager(String eventType) {
        return managerMap.getOrDefault(eventType, null);
    }
}
