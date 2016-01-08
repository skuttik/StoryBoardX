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
import javafx.stage.Stage;
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
    private double w = 1500;

    public StoryBoardX(Group root, double w) {
        mainPane = new Pane();
        barList = new ArrayList<>();
        this.w = w;
        init(root);
    }

    private void init(Group root) {
        
        mainPane.prefWidthProperty().bind(root.getScene().widthProperty());
        timeBar = new SBXTimeBar(mainPane.prefWidthProperty());
        
        
        SBXManager.getInstance().setTimeBar(timeBar);
        barBoxY = timeBar.getBoundsInLocal().getHeight() * 3;
        viewerY = barBoxY;

        viewer = new SBXEventViewer(w);
        SBXManager.getInstance().setViewer(viewer);

        mainPane.getChildren().addAll(timeBar, viewer);

        SBXActionHandler handler = new SBXActionHandler();

        mainPane.setOnMouseMoved(handler.getMouseHandler());
        mainPane.setOnMouseExited(handler.getMouseHandler());
        mainPane.setOnMouseExited(handler.getMouseHandler());

        mainPane.setOnMouseDragged(handler.getMouseHandler());
        mainPane.setOnMouseDragExited(handler.getMouseHandler());

//        mainPane.setOnDragDone(handler.getDragHandler());
//        mainPane.setOnDragOver(handler.getDragHandler());
//        mainPane.setOnKeyPressed(handler.getKeyHandler());
//        mainPane.setOnZoom(handler.getZoomHandler());
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
        timeBar.setLineVerticalPosition(barBoxY, viewerY - barBoxY);
    }

    public void addEventBar(Date sd, long td) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
