/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx;

import java.util.ArrayList;
import java.util.Date;
import storyboardx.ui.SBXEventViewer;
import storyboardx.ui.SBXEventBar;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import storyboardx.ui.SBXActionHandler;
import storyboardx.ui.SBXEventItem;
import storyboardx.ui.SBXShotPreview;
import storyboardx.ui.SBXShotsBar;
import storyboardx.ui.SBXTimeBar;

/**
 *
 * @author skuttik
 */
public class StoryBoardX {

    private final ArrayList<SBXEventBar> eventBarList;
    private final ArrayList<SBXShotsBar> shotBarList;
    private double barBoxY = 0.0;
    private SBXEventViewer viewer;
    private SBXTimeBar timeBar;
    private final Pane mainPane;
    private double w = 1500;
    private Rectangle shotPreviewRectangle;

    public StoryBoardX(Group root, double w) {
        mainPane = new Pane();
        eventBarList = new ArrayList<>();
        shotBarList = new ArrayList<>();
        this.w = w;
        init(root);
    }

    private void init(Group root) {
        mainPane.prefWidthProperty().bind(root.getScene().widthProperty());
        mainPane.prefHeightProperty().bind(root.getScene().heightProperty());

        timeBar = new SBXTimeBar(mainPane.prefWidthProperty());
        SBXManager.getInstance().setTimeBar(timeBar);
        barBoxY = timeBar.getBoundsInLocal().getHeight() * 3;

        viewer = new SBXEventViewer(mainPane.prefWidthProperty());
        SBXManager.getInstance().setViewer(viewer);
        mainPane.getChildren().addAll(timeBar, viewer);

        SBXShotPreview shotPreview = new SBXShotPreview(200, mainPane.widthProperty());
        shotPreviewRectangle = shotPreview.getRectangle();
        SBXManager.getInstance().setShotPreview(shotPreview);

        SBXActionHandler handler = new SBXActionHandler();

        mainPane.setOnMouseMoved(handler.getMouseHandler());
        mainPane.setOnMouseExited(handler.getMouseHandler());
        mainPane.setOnMouseExited(handler.getMouseHandler());

        mainPane.setOnMouseDragged(handler.getMouseHandler());
        mainPane.setOnMouseDragExited(handler.getMouseHandler());

        root.getChildren().addAll(mainPane);
    }

    public void addShotBar(int height, Date start, int duration) {
        SBXShotsBar bar = new SBXShotsBar(height, mainPane.widthProperty());
        shotBarList.add(bar);
        organizeBars();
    }

    public void addEventBar(String eventType, int height, Date start, int duration) {
        SBXEventBar bar = new SBXEventBar(height, eventType, mainPane.widthProperty(), SBXEventItem.ColorScheme.SKY);
        bar.init(start, duration);
        eventBarList.add(bar);
        organizeBars();
    }

    public void organizeBars() {
        mainPane.getChildren().removeAll(timeBar, viewer, shotPreviewRectangle);
        mainPane.getChildren().removeAll(shotBarList);
        mainPane.getChildren().removeAll(eventBarList);
        double ly = barBoxY;
        for (SBXShotsBar sb : shotBarList) {
            sb.setLayoutY(ly + 2);
            ly += sb.getBoundsInLocal().getHeight() + 2;
            mainPane.getChildren().add(sb);
        }
        for (SBXEventBar eb : eventBarList) {
            eb.setLayoutY(ly + 2);
            ly += eb.getBoundsInLocal().getHeight() + 2;
            mainPane.getChildren().add(eb);
        }
        mainPane.getChildren().addAll(viewer, shotPreviewRectangle, timeBar);
        timeBar.setLineVerticalPosition(barBoxY, ly - barBoxY);
        viewer.setLayoutY(ly + 2);
    }
}
