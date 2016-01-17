/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.ui;

import java.text.DateFormat;
import java.util.Date;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    public SBXTimeBar(ReadOnlyDoubleProperty width) {

        prefWidthProperty().bind(width);
        start = new Text("");
        start.setFill(Color.ALICEBLUE);
        end = new Text("");
        end.setFill(Color.ALICEBLUE);

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
        start.setLayoutY(start.getBoundsInLocal().getHeight());

        end.setText(DateFormat.getDateTimeInstance().format(new Date(startDate.getTime() + totalDuration)));
        end.layoutXProperty().bind(prefWidthProperty().subtract(end.getBoundsInLocal().getWidth()));
        end.setLayoutY(end.getBoundsInLocal().getHeight());

        currentText.setLayoutY(end.getBoundsInLocal().getHeight() * 2);
        currentArrow.setLayoutY(end.getBoundsInLocal().getHeight() * 2.3);
        currentLine.setLayoutY(0);
    }

    public void updateCurrentDate(long startDate, long totalDuration, double x) {
        double currW = prefWidthProperty().get();
        double factor = x / currW;
        long delta = (long) (factor * totalDuration);
        Date currentDate = new Date(startDate + delta);
        currentText.setText(DateFormat.getDateTimeInstance().format(currentDate) + " (" + Math.round(factor * 100.0) + "%)");
        int dim = (int) (currentText.getBoundsInLocal().getWidth());
        currentText.setLayoutX(x - (dim * x / currW));
        currentArrow.setLayoutX(x);
        currentArrow.setVisible(true);
        currentLine.setLayoutX(x);
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
