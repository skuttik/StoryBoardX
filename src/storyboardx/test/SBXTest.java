/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storyboardx.test;

import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import storyboardx.SBXManager;
import storyboardx.StoryBoardX;
import storyboardx.ui.SBXEventViewer;

/**
 *
 * @author skuttik
 */
public class SBXTest extends Application {

    public SBXTest() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        double w = 1400;
        int h = 10;
//        primaryStage.setWidth(w);
        Scene scene = new Scene(root, w, 100);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);

        StoryBoardX sbx = new StoryBoardX(root, primaryStage.getWidth());

        int td = 120000;
        Date sd = new Date(new Date().getTime() - td);
        double scale = w / td;

        createEvents("test", 5, sd.getTime(), td);
        sbx.addEventBar("test", h, sd, td);

        createEvents("abc", 8, sd.getTime(), td);
        sbx.addEventBar("abc", h, sd, td);

        createEvents("qwerty", 8, sd.getTime(), td);
        sbx.addEventBar("qwerty", h, sd, td);

        createEvents("poi", 8, sd.getTime(), td);
        sbx.addEventBar("poi", h, sd, td);

        createEvents("kkk", 5, sd.getTime(), td);
        sbx.addEventBar("kkk", h, sd, td);

        createEvents("ttt", 8, sd.getTime(), td);
        sbx.addEventBar("ttt", h, sd, td);

        createEvents("zxc", 8, sd.getTime(), td);
        sbx.addEventBar("zxc", h, sd, td);

        primaryStage.setHeight(50 + SBXManager.getInstance().getEventTypes().size() * 45 + SBXEventViewer.height);
        primaryStage.show();
        SBXManager.getInstance().setTimeWindow(sd, td);
    }

    private void createEvents(String type, int num, long startTime, long duration) {
        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Long> ntimes = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            times.add((long) (Math.random() * duration + startTime - (duration * .1)));
        }

        times.sort(Long::compareTo);
        for (int i = 1; i < num; i++) {
            ntimes.add(times.get(i));
        }
        ntimes.add(duration + startTime);

        SBXEventRepositoryImpl db = new SBXEventRepositoryImpl(type);
        SBXManager.getInstance().addEventManager(type, db);
        for (int i = 0; i < num; i++) {
            db.addEvent(" event #" + i, times.get(i), (int) (Math.random() * (ntimes.get(i).doubleValue() - times.get(i).doubleValue())));
        }

    }
}
