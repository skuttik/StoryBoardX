/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx;

import java.util.ArrayList;
import storyboardx.ui.SBXEventViewer;
import storyboardx.ui.SBXEventBar;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import storyboardx.ui.SBXActionHandler;
import storyboardx.ui.SBXTimeBar;

/**
 *
 * @author skuttik
 */
public class StoryBoardX {

    private final ArrayList<SBXEventBar> barList;
    private double barBoxY = 0.0;
    private double viewerY = 0.0;
    private SBXEventViewer viewer;
    private SBXTimeBar timeBar;
    private final Pane mainPane;

    public StoryBoardX(Group root) {
        mainPane = new Pane();
        barList = new ArrayList<>();
        init(root);
    }

    private void init(Group root) {
        double w = 1400;
        timeBar = new SBXTimeBar(w);
        SBXManager.getInstance().setTimeBar(timeBar);
        barBoxY = timeBar.getBoundsInLocal().getHeight() * 3;
        viewerY = barBoxY;

        viewer = new SBXEventViewer(w);
        SBXManager.getInstance().setViewer(viewer);

        mainPane.getChildren().addAll(timeBar, viewer);

        SBXActionHandler handler = new SBXActionHandler();

        mainPane.setOnMouseMoved(handler.getMouseHandler());
        mainPane.setOnMouseExited(handler.getMouseHandler());
         //        mainpane.setOnDragDetected(handler.getMouseHandler());
        //        mainpane.setOnKeyPressed(handler.getKeyHandler());
        //        mainpane.setOnZoom(handler.getZoomHandler());
        //        mainpane.setOnDragOver(handler.getDragHandler());

         //        Line line = new Line(0, 0, 0, 100);
        //        line.setFill(Color.MAGENTA);
        //        line.setTranslateX(333);
        //        barContainer.getChildren().add(line);
        root.getChildren().addAll(mainPane);
    }

    public void addEventBar(SBXEventBar bar) {
        mainPane.getChildren().removeAll(viewer, timeBar);
        bar.setTranslateY(viewerY);
        mainPane.getChildren().add(bar);
        barList.add(bar);
        viewerY += bar.getBoundsInLocal().getHeight() + 1;
        viewer.setTranslateY(viewerY + 2);
        mainPane.getChildren().addAll(viewer, timeBar);
        timeBar.setLineVerticalPosition(barBoxY, viewerY-barBoxY);
    }
}
