/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.util.ArrayList;
import java.util.Date;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import storyboardx.SBXManager;
import storyboardx.data.SBXEvent;
import storyboardx.data.SBXEventListener;
import storyboardx.data.SBXEventManager;

/**
 *
 * @author skuttik
 */
public class SBXEventBar extends Pane implements SBXEventListener {

    private final double space;
    private double scale;
    private final double height;
    private int numOfEvents;
    private Date startDate;
    private final SBXEventManager manager;

    public SBXEventBar(double height, String eventType) {
        this.height = height;
        this.space = Math.rint(height / 3.0);
        numOfEvents = 0;
        scale = 0;
        if (eventType != null && !eventType.isEmpty()) {
            manager = SBXManager.getInstance().getEventManager(eventType);
        } else {
            manager = null;
        }
    }

    public void init(Date startDate, long totalDuration, double scale) {
        double width = (double) totalDuration * scale;
//        double width =   mainPane.prefWidthProperty().bind(root.getScene().widthProperty());
        this.startDate = startDate;
        this.scale = scale;
        
        Rectangle bar = new Rectangle(width, height + 2.0 * space, Color.DIMGREY);
        bar.setStrokeWidth(height * .06);
        bar.setStrokeType(StrokeType.INSIDE);
        bar.setStroke(Color.DARKGRAY);
        getChildren().add(bar);
        
        clearAllEvents();
        if (manager != null) {
            manager.getFilteredEvents(startDate, new Date(startDate.getTime() + totalDuration), null, null)
                    .stream()
                    .forEach((ev) -> {
                        addEvent(ev);
                    });
        }
    }

    public void resetEvents(ArrayList<SBXEvent> events) {
        clearAllEvents();
        events.stream().forEach(ev -> {
            addEvent(ev);
        });
    }

    public void clearAllEvents() {
        if (numOfEvents > 0) {
            getChildren().remove(1, numOfEvents);
            numOfEvents = 0;
        }
    }

    public void addEvent(SBXEvent event) {
        if (scale != 0) {
            getChildren().add(new SBXEventItem(scale, height, space, startDate, event).getShape());
            numOfEvents++;
        } else {
            System.err.println(SBXEventBar.class.getSimpleName() + ".addEvent(...) called before " + SBXEventBar.class.getSimpleName() + ".init(...)");
        }
    }

    @Override
    public void onNewEvent(SBXEvent event) {
        addEvent(event);
    }
}
