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
import javafx.scene.layout.VBox;
import storyboardx.ui.SBXActionHandler;
import storyboardx.ui.SBXTimeBar;

/**
 *
 * @author skuttik
 */
public class StoryBoardX {

    private final VBox barBox;
    private final ScrollPane barContainer;
    private final ArrayList<SBXEventBar> barList;

    public StoryBoardX(Group root) {
        barBox = new VBox();
        barContainer = new ScrollPane();
        barList = new ArrayList<>();
        init(root);
    }

    private void init(Group root) {
        double w = 1400;

        AnchorPane mainPane = new AnchorPane();

        BorderPane upperPane = new BorderPane();
        SBXTimeBar timeBar = new SBXTimeBar(w, 20);
        SBXManager.getInstance().setTimeBar(timeBar);
        upperPane.setTop(timeBar);
        barContainer.setContent(barBox);
        upperPane.setCenter(barContainer);

        SBXEventViewer viewer = new SBXEventViewer(w);
        SBXManager.getInstance().setViewer(viewer);

        AnchorPane.setTopAnchor(upperPane, 0.0);
        AnchorPane.setBottomAnchor(viewer, -60.0);
        mainPane.getChildren().addAll(upperPane, viewer);

        root.getChildren().addAll(mainPane);

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
    }

    public void addEventBar(SBXEventBar bar) {
        barBox.getChildren().add(bar);
        barList.add(bar);
    }
}
