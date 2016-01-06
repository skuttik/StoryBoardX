/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import storyboardx.SBXManager;

/**
 *
 * @author skuttik
 */
public class SBXActionHandler {

    private final EventHandler<MouseEvent> mouseHandler;
    private final EventHandler<KeyEvent> keyHandler;
    private final EventHandler<ZoomEvent> zoomHandler;
    private final EventHandler<DragEvent> dragHandler;

    public SBXActionHandler() {
        mouseHandler = (MouseEvent event) -> {
            switch (event.getEventType().getName()) {
                case "MOUSE_MOVED":
                    SBXManager.getInstance().setCurrentPointer(event.getSceneX());
                    break;
                case "MOUSE_EXITED":
                    SBXManager.getInstance().setCurrentPointer(-1.0);
                    break;
                default:
                    System.out.println("mouse: " + event.getEventType().getName());
            }
        };

        keyHandler = (KeyEvent event) -> {
            System.out.println("key");
        };

        zoomHandler = (ZoomEvent event) -> {
            System.out.println("zoom");
        };

        dragHandler = (DragEvent event) -> {
            System.out.println("drag: " + event.getX());

        };
    }

    public EventHandler<MouseEvent> getMouseHandler() {
        return mouseHandler;
    }

    public EventHandler<KeyEvent> getKeyHandler() {
        return keyHandler;
    }

    public EventHandler<ZoomEvent> getZoomHandler() {
        return zoomHandler;
    }

    public EventHandler<DragEvent> getDragHandler() {
        return dragHandler;
    }

}
