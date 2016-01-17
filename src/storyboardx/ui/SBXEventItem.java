/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableValue;
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

    private final SBXEvent event;
    private final double refHeight;
    private final double circleRadius;
    private final double refSpace;
    private final Date startDate;
    private final ReadOnlyDoubleProperty refSize;
    private final int totalDuration;
    private final ArrayList<Shape> shapes;
    private final ArrayList<Color> borderColors;
    private final ArrayList<Color> fillColors;
    private final ArrayList<StrokeType> stroke;
    private boolean selected;
    private boolean mousein;

    public enum ColorScheme {

        LIGHTYELLOW,
        SKY
    };

    private enum DisplayMode {

        DEFAULT_MODE,
        HIGHLIGHTED_MODE,
        SELECTED_MODE,
        HIGHLIGHTED_SELECTED_MODE
    }

    public SBXEventItem(double refHeight, double refSpace, Date startDate, int totalDuration, SBXEvent event, ReadOnlyDoubleProperty refSize, ColorScheme colScheme) {
        this.refHeight = refHeight;
        this.circleRadius = refHeight * .35;
        this.refSpace = refSpace;
        this.startDate = startDate;
        this.totalDuration = totalDuration;
        this.event = event;
        this.refSize = refSize;
        this.shapes = new ArrayList<>();
        this.borderColors = new ArrayList<>();
        this.fillColors = new ArrayList<>();
        switch (colScheme) {
            case LIGHTYELLOW:
                this.borderColors.add(DisplayMode.DEFAULT_MODE.ordinal(), Color.LIGHTYELLOW);
                this.borderColors.add(DisplayMode.HIGHLIGHTED_MODE.ordinal(), Color.RED);
                this.borderColors.add(DisplayMode.SELECTED_MODE.ordinal(), Color.GREENYELLOW);
                this.borderColors.add(DisplayMode.HIGHLIGHTED_SELECTED_MODE.ordinal(), Color.RED);

                this.fillColors.add(DisplayMode.DEFAULT_MODE.ordinal(), Color.BURLYWOOD);
                this.fillColors.add(DisplayMode.HIGHLIGHTED_MODE.ordinal(), Color.BURLYWOOD);
                this.fillColors.add(DisplayMode.SELECTED_MODE.ordinal(), Color.LIGHTYELLOW);
                this.fillColors.add(DisplayMode.HIGHLIGHTED_SELECTED_MODE.ordinal(), Color.LIGHTYELLOW);

                break;

            case SKY:
                this.borderColors.add(DisplayMode.DEFAULT_MODE.ordinal(), Color.LINEN);
                this.borderColors.add(DisplayMode.HIGHLIGHTED_MODE.ordinal(), Color.RED);
                this.borderColors.add(DisplayMode.SELECTED_MODE.ordinal(), Color.LIME);
                this.borderColors.add(DisplayMode.HIGHLIGHTED_SELECTED_MODE.ordinal(), Color.RED);

                this.fillColors.add(DisplayMode.DEFAULT_MODE.ordinal(), Color.CORNFLOWERBLUE);
                this.fillColors.add(DisplayMode.HIGHLIGHTED_MODE.ordinal(), Color.CORNFLOWERBLUE);
                this.fillColors.add(DisplayMode.SELECTED_MODE.ordinal(), Color.BLUE);
                this.fillColors.add(DisplayMode.HIGHLIGHTED_SELECTED_MODE.ordinal(), Color.BLUE);

                break;

            default:
        }

        this.stroke = new ArrayList<>();
        stroke.add(DisplayMode.DEFAULT_MODE.ordinal(), StrokeType.INSIDE);
        stroke.add(DisplayMode.HIGHLIGHTED_MODE.ordinal(), StrokeType.OUTSIDE);
        stroke.add(DisplayMode.SELECTED_MODE.ordinal(), StrokeType.INSIDE);
        stroke.add(DisplayMode.HIGHLIGHTED_SELECTED_MODE.ordinal(), StrokeType.OUTSIDE);

        create();
    }

    private void attachEvents() {
        shapes.forEach(s -> {
            s.setOnMouseClicked(this);
            s.setOnMouseEntered(this);
            s.setOnMouseExited(this);
        });
        refSize.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            redraw();
        });
    }

    @Override
    public void handle(MouseEvent mev) {
        switch (mev.getEventType().getName()) {
            case "MOUSE_ENTERED":
                mousein = true;
                SBXManager.getInstance().displayEventInfo(event);
                break;
            case "MOUSE_EXITED":
                mousein = false;
                SBXManager.getInstance().clearEventInfo();
                break;
            case "MOUSE_CLICKED":
                selected = !selected;
                System.out.println(event.getDescription());
                break;
            default:
                System.out.println(mev.getEventType().getName());
                break;
        }
        if (selected) {
            if (mousein) {
                setMode(DisplayMode.HIGHLIGHTED_SELECTED_MODE);
            } else {
                setMode(DisplayMode.SELECTED_MODE);
            }
        } else {
            if (mousein) {
                setMode(DisplayMode.HIGHLIGHTED_MODE);
            } else {
                setMode(DisplayMode.DEFAULT_MODE);
            }
        }
    }

    public ArrayList<Shape> getShape() {
        return shapes;
    }

    private void setMode(DisplayMode mode) {
        shapes.forEach(s -> {
            s.setStroke(borderColors.get(mode.ordinal()));
            s.setStrokeType(StrokeType.OUTSIDE);
            s.setFill(fillColors.get(mode.ordinal()));
            if (mode == DisplayMode.HIGHLIGHTED_MODE || mode == DisplayMode.HIGHLIGHTED_SELECTED_MODE) {
                s.setStrokeWidth(refHeight * .2);
            } else {
                s.setStrokeWidth(refHeight * .08);
            }
        });
    }

    private void create() {
        Circle circle1 = new Circle(circleRadius);
        circle1.setStrokeWidth(refHeight * .08);
        circle1.setCenterY(refSpace + refHeight / 2.0);
        circle1.setVisible(false);
        shapes.add(circle1);

        Rectangle rect1 = new Rectangle(refHeight, refHeight);
        rect1.setY(refSpace);
        rect1.setStrokeWidth(refHeight * .08);
        rect1.setVisible(false);
        shapes.add(rect1);

        selected = false;
        setMode(DisplayMode.DEFAULT_MODE);
        attachEvents();
    }

    private void redraw() {
        double factor = refSize.get() / totalDuration;
        double len = event.getDuration() * factor;
        double pos = (double) (event.getRefTime().getTime() - startDate.getTime()) * factor;
        if (len < circleRadius) {
            shapes.get(0).setVisible(true);
            ((Circle) shapes.get(0)).setCenterX(pos);
            shapes.get(1).setVisible(false);
        } else {
            shapes.get(0).setVisible(false);
            shapes.get(1).setVisible(true);
            ((Rectangle) shapes.get(1)).setWidth(len);
            ((Rectangle) shapes.get(1)).setX(pos);
        }
    }
}
