/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import storyboardx.SBXManager;

/**
 *
 * @author skuttik
 */
public class SBXShotItem implements EventHandler<MouseEvent> {

    Rectangle frameRect;
    double percent;
    double height;
    double frameW;
    double posX;

    public SBXShotItem(double frameH, double frameW, double posX, double percent) {
        frameRect = new Rectangle(frameW, frameH, Color.DIMGRAY);
        frameRect.setStrokeWidth(frameH * .03);
        frameRect.setStrokeType(StrokeType.INSIDE);
        frameRect.setStroke(Color.DARKGRAY);
        frameRect.setLayoutX(posX);
        frameRect.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("resources/noFrame.png"))));
        this.percent = percent;
        this.height = frameH;
        this.frameW = frameW;
        this.posX = posX;
        attachEvents();
    }

    public Rectangle getItem() {
        return frameRect;
    }

    private void attachEvents() {
        frameRect.setOnMouseClicked(this);
        frameRect.setOnMouseEntered(this);
        frameRect.setOnMouseExited(this);
        frameRect.setOnMouseMoved(this);
    }

    @Override
    public void handle(MouseEvent event) {
        switch (event.getEventType().getName()) {
            case "MOUSE_ENTERED":
//                SBXManager.getInstance().displayEventInfo(event);
                SBXManager.getInstance().showShotPreview(null, event.getSceneX(), event.getSceneY());
                break;
            case "MOUSE_EXITED":
                SBXManager.getInstance().clearEventInfo();
                SBXManager.getInstance().hideShotPreview();
                break;
            case "MOUSE_CLICKED":
                break;
            case "MOUSE_MOVED":
                SBXManager.getInstance().movePreview(event.getSceneX(), event.getSceneY());
                break;
            default:
                System.out.println(event.getEventType().getName());
                break;
        }
    }

}
