/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import storyboardx.SBXManager;
import storyboardx.data.SBXEvent;
import storyboardx.data.SBXEventListener;
import storyboardx.data.SBXEventManager;
import storyboardx.ui.SBXEventItem.ColorScheme;

/**
 *
 * @author skuttik
 */
public class SBXEventBar extends Pane implements SBXEventListener {

    private final double space;
    private final double height;
    private int numOfEvents;
    private Date startDate;
    private int totalDuration;
    private final SBXEventManager manager;
    private final ReadOnlyDoubleProperty refSize;
    private final ColorScheme colorScheme;

    public SBXEventBar(int height, String eventType, ReadOnlyDoubleProperty refSize, ColorScheme colorScheme) {
        this.height = height;
        this.space = Math.rint(height / 3.0);
        this.refSize = refSize;
        this.colorScheme = colorScheme;
        numOfEvents = 0;
        if (eventType != null && !eventType.isEmpty()) {
            manager = SBXManager.getInstance().getEventManager(eventType);
        } else {
            manager = null;
        }

        prefWidthProperty().bind(refSize);

        Rectangle bar = new Rectangle(2, height + 2.0 * space, Color.DIMGREY);
        bar.widthProperty().bind(refSize);
        bar.setStrokeWidth(height * .06);
        bar.setStrokeType(StrokeType.INSIDE);
        bar.setStroke(Color.DARKGRAY);
        getChildren().add(bar);
    }

    public void init(Date startDate, int totalDuration) {
        this.startDate = startDate;
        this.totalDuration = totalDuration;

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
        SBXEventItem item = new SBXEventItem(height, space, startDate, totalDuration, event, refSize, colorScheme);
        getChildren().addAll(item.getShape());
        numOfEvents++;
    }

    @Override
    public void onNewEvent(SBXEvent event) {
        addEvent(event);
    }
}
