/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author skuttik
 */
public class SBXShotPreview extends Pane {

    private final Rectangle shotPreviewRect;
    private final double width;

    public SBXShotPreview(double width, ReadOnlyDoubleProperty sceneWidth) {
        double height = width * 9.0 / 16.0;
        shotPreviewRect = new Rectangle(width, height, Color.DIMGRAY);
        shotPreviewRect.setStrokeWidth(3);
        shotPreviewRect.setStrokeType(StrokeType.CENTERED);
        shotPreviewRect.setStroke(Color.DARKGRAY);
        shotPreviewRect.setLayoutY(20);
        shotPreviewRect.setFill(Color.BISQUE);
        shotPreviewRect.setLayoutX(100);
        shotPreviewRect.setMouseTransparent(true);
        this.width = width;
        hide();
        prefWidthProperty().bind(sceneWidth);
    }

    public void hide() {
        shotPreviewRect.setVisible(false);
    }

    public void show(ImagePattern imagePattern, double positionX, double positionY) {
//        shotPreviewRect.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("resources/noFrame.png"))));
        shotPreviewRect.setVisible(true);
        moveTo(positionX, positionY);
    }

    public Rectangle getRectangle() {
        return shotPreviewRect;
    }

    public void moveTo(double positionX, double positionY) {
        double perc = positionX / getPrefWidth();
        shotPreviewRect.setLayoutX((getPrefWidth() - width) * perc);
        shotPreviewRect.setLayoutY(positionY);
    }
}
