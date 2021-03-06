/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.text.DateFormat;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import storyboardx.data.SBXEvent;

/**
 *
 * @author skuttik
 */
public class SBXEventViewer extends Pane {

    public static double height = 56;
    private final Text identification;
    private final Text timeInfo;
    private final Text description;
    private final ImageView icon1;
    private final ImageView icon2;
    private final ImageView icon3;
    private final Rectangle backgroundBar;

    public SBXEventViewer(ReadOnlyDoubleProperty width) {
        HBox main = new HBox(5);

        icon1 = new ImageView(new Image(getClass().getResourceAsStream("resources/bell.png")));
        icon1.translateYProperty().set(4);
        icon1.setVisible(false);

        identification = new Text("");
        identification.setFill(Color.BEIGE);

        icon2 = new ImageView(new Image(getClass().getResourceAsStream("resources/stopwatch.png")));
        icon2.translateYProperty().set(4);
        icon2.setVisible(false);

        timeInfo = new Text("");
        timeInfo.setFill(Color.BEIGE);
        timeInfo.setWrappingWidth(160);

        icon3 = new ImageView(new Image(getClass().getResourceAsStream("resources/info.png")));
        icon3.translateYProperty().set(4);
        icon3.setVisible(false);

        description = new Text("");
        description.setFill(Color.BEIGE);

        main.getChildren().addAll(new Text(""), icon1, identification, new Text(""), icon2, timeInfo, new Text(""), icon3, description);

        backgroundBar = new Rectangle(10, height, new Color(0.9, 0.9, 0.9, 0.4));

        getChildren().addAll(backgroundBar, main);

        backgroundBar.widthProperty().bind(width);
        
        setMouseTransparent(true);
        clearEventInfo();
    }

    public final void clearEventInfo() {
        backgroundBar.setVisible(false);
        icon1.setVisible(false);
        identification.setText("");
        icon2.setVisible(false);
        timeInfo.setText("");
        icon3.setVisible(false);
        description.setText("");
    }

    public void displayEventInfo(SBXEvent event) {
        backgroundBar.setVisible(true);
        icon1.setVisible(true);
        icon2.setVisible(true);
        icon3.setVisible(true);

        identification.setText(event.getType() + ":\n   " + event.getName());
        timeInfo.setText(DateFormat.getDateTimeInstance().format(event.getRefTime()) + "\n" + event.getDuration() / 1000.0 + " s");
        description.setText(event.getDescription()+"\n");

        icon1.translateYProperty().set((height - icon1.getBoundsInLocal().getHeight()) / 2.0);
        icon2.translateYProperty().set((height - icon2.getBoundsInLocal().getHeight()) / 2.0);
        icon3.translateYProperty().set((height - icon3.getBoundsInLocal().getHeight()) / 2.0);
        identification.translateYProperty().set((height - identification.getBoundsInLocal().getHeight()) / 2.0);
        timeInfo.translateYProperty().set((height - timeInfo.getBoundsInLocal().getHeight()) / 2.0);
        description.translateYProperty().set((height - description.getBoundsInLocal().getHeight()) / 2.0);

    }

}
