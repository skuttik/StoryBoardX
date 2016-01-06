/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.text.DateFormat;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 *
 * @author skuttik
 */
public class SBXTimeBar extends Pane {

    private final Text start;
    private final Text end;
    private final Text currentText;
    private final Polygon currentArrow;
    private final Polygon currentLine;
    private final double width;

    public SBXTimeBar(double width) {
        this.width = width;

        VBox main = new VBox(5);
        TilePane upper = new TilePane();
        Pane lower = new Pane();

        start = new Text("");
        start.setFill(Color.ALICEBLUE);
        end = new Text("");
        end.setFill(Color.ALICEBLUE);

        upper.setPrefColumns(2);
        upper.setAlignment(Pos.CENTER);
        upper.setMinSize(width, 30);
        HBox sp = new HBox(start);
        sp.setMinWidth(width / 2);
        sp.setAlignment(Pos.BOTTOM_LEFT);
        HBox ep = new HBox(end);
        ep.setMinWidth(width / 2);
        ep.setAlignment(Pos.BOTTOM_RIGHT);
        upper.getChildren().addAll(sp, ep);

        currentText = new Text("");
        currentText.setFill(Color.ALICEBLUE);

        currentArrow = new Polygon(-6, 0, 6, 0, 0, 12);
        currentArrow.setFill(Color.ALICEBLUE);

        currentLine = new Polygon();
        currentLine.setFill(new Color(1.0, 1.0, 1.0, 1.0));
        currentLine.setStroke(new Color(1.0, 1.0, 1.0, 1.0));
        currentLine.setBlendMode(BlendMode.EXCLUSION);

        getChildren().addAll(start, end, currentArrow, currentText, currentLine);
        setMouseTransparent(true);

        clearCurrentDate();
    }

    public void set(Date startDate, long totalDuration) {
        start.setText(DateFormat.getDateTimeInstance().format(startDate));
        end.setText(DateFormat.getDateTimeInstance().format(new Date(startDate.getTime() + totalDuration)));
        start.setTranslateY(start.getBoundsInLocal().getHeight());
        end.setTranslateX(width - end.getBoundsInLocal().getWidth());
        end.setTranslateY(end.getBoundsInLocal().getHeight());
        currentText.setTranslateY(end.getBoundsInLocal().getHeight() * 2);
        currentArrow.setTranslateY(end.getBoundsInLocal().getHeight() * 2.3);
        currentLine.setTranslateY(0);
    }

    public void updateCurrentDate(Date currentDate, double x) {
        currentText.setText(DateFormat.getDateTimeInstance().format(currentDate)+" ("+(int)(x/width*100.0)+"%)");
        int dim = (int) (currentText.getBoundsInLocal().getWidth());
        currentText.setTranslateX(x - (dim * x / width));
        currentArrow.setTranslateX(x);
        currentArrow.setVisible(true);
        currentLine.setTranslateX(x);
        currentLine.setVisible(true);
    }

    public final void clearCurrentDate() {
        currentText.setText("");
        currentArrow.setVisible(false);
        currentLine.setVisible(false);
    }

    public void setLineVerticalPosition(double lineY, double lineH) {
        double lineW = 1;

        currentLine.getPoints().remove(0, currentLine.getPoints().size());

        currentLine.getPoints().add(-lineW);
        currentLine.getPoints().add(lineY);

        currentLine.getPoints().add(lineW);
        currentLine.getPoints().add(lineY);

        currentLine.getPoints().add(lineW);
        currentLine.getPoints().add(lineY + lineH);

        currentLine.getPoints().add(-lineW);
        currentLine.getPoints().add(lineY + lineH);
    }
}
