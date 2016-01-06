/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.util.Date;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import storyboardx.data.SBXEvent;
import storyboardx.SBXManager;

/**
 *
 * @author skuttik
 */
public class SBXEventItem implements EventHandler<MouseEvent> {

    private final double refHeight;
    private final double refSpace;
    private final Rectangle rect1;
    private final Circle circle1;
    private final SBXEvent event;

    public SBXEventItem(double scale, double refHeight, double refSpace, Date startDate, SBXEvent event) {
        double len = event.getDuration() * scale;
        double pos = (double) (event.getRefTime().getTime() - startDate.getTime()) * scale;
        this.event = event;
        this.refHeight = refHeight;
        this.refSpace = refSpace;
        if (len < refHeight) {
            circle1 = new Circle(refHeight * 0.4);
            circle1.setFill(Color.STEELBLUE);
            circle1.setCenterX(pos);
            circle1.setCenterY(refSpace + refHeight / 2.0);
            circle1.setStroke(Color.LIGHTSKYBLUE);
            circle1.setStrokeWidth(refHeight * .08);
            circle1.setStrokeType(StrokeType.INSIDE);
            rect1 = null;
        } else {
            rect1 = new Rectangle(len, refHeight);
            rect1.setFill(Color.BURLYWOOD);
            rect1.setX(pos);
            rect1.setY(refSpace);
            rect1.setStroke(Color.LIGHTYELLOW);
            rect1.setStrokeWidth(refHeight * .08);
            rect1.setStrokeType(StrokeType.INSIDE);
            circle1 = null;
        }
        attachEvents();
    }

    private void attachEvents() {
        getShape().setOnMouseClicked(this);
        getShape().setOnMouseEntered(this);
        getShape().setOnMouseExited(this);
    }

    @Override
    public void handle(MouseEvent mev) {
//        System.out.println(mev.getEventType().getName() + ":" + event.getDescrption());
        switch (mev.getEventType().getName()) {
            case "MOUSE_ENTERED":
                setMode(true);
                SBXManager.getInstance().displayEventInfo(event);
                break;
            case "MOUSE_EXITED":
                setMode(false);
                SBXManager.getInstance().clearEventInfo();
                break;
            case "MOUSE_CLICKED":
                System.out.println(event.getDescription());
                break;
            default:
                System.out.println(mev.getEventType().getName());
                break;
        }
    }

    public Shape getShape() {
        if (rect1 != null) {
            return rect1;
        } else {
            return circle1;
        }
    }

    private void setMode(boolean highlightened) {
        if (rect1 != null) {
            if (highlightened) {
                rect1.setStroke(Color.RED);
                rect1.setStrokeType(StrokeType.OUTSIDE);
            } else {
                rect1.setStroke(Color.LIGHTYELLOW);
                rect1.setStrokeType(StrokeType.INSIDE);
            }
        } else {
            if (highlightened) {
                circle1.setStroke(Color.RED);
                circle1.setStrokeType(StrokeType.OUTSIDE);
            } else {
                circle1.setStroke(Color.LIGHTSKYBLUE);
                circle1.setStrokeType(StrokeType.INSIDE);
            }
        }
    }
}
