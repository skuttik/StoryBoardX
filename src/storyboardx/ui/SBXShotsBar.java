/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import storyboardx.data.SBXEvent;
import storyboardx.data.SBXEventListener;

/**
 *
 * @author skuttik
 */
public class SBXShotsBar extends Pane implements SBXEventListener {

    private final double height;
    private final ReadOnlyDoubleProperty refSize;

    public SBXShotsBar(int height, ReadOnlyDoubleProperty refSize) {
        this.height = height;
        this.refSize = refSize;
        prefWidthProperty().bind(refSize);

        Rectangle bar = new Rectangle(2, height, Color.DIMGRAY);
        bar.widthProperty().bind(refSize);
        bar.setStrokeWidth(height * .03);
        bar.setStrokeType(StrokeType.INSIDE);
        bar.setStroke(Color.DARKGRAY);
        getChildren().add(bar);

        refSize.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            redraw(newValue.doubleValue());
        });
    }

    @Override
    public void onNewEvent(SBXEvent event) {
        System.out.println("event : " + event);
    }

    private void redraw(double width) {
        getChildren().remove(1, getChildren().size());
        double frameW = height * 16 / 9;
        int frameNum = (int) Math.floor((width * .9) / frameW);
        int space = (int) Math.floor((width - frameNum * frameW) / (frameNum - 1));
        double posX = 0;
        for (int i = 0; i < frameNum; i++) {
            Rectangle frameRect = new Rectangle(frameW, height, Color.DIMGRAY);
            frameRect.setStrokeWidth(height * .03);
            frameRect.setStrokeType(StrokeType.INSIDE);
            frameRect.setStroke(Color.DARKGRAY);
            frameRect.setLayoutX(posX);
            getChildren().add(frameRect);
            frameRect.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("resources/emptyFrame.png"))));
            posX += frameW + space;
        }
    }

}
