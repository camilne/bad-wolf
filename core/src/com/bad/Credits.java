package com.bad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * @author Cameron Milne
 * @version 1.0.0
 */
public class Credits extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        MediaView mediaView = new MediaView();
        Media media = new Media(new File("credits/end_scene.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaView.setPreserveRatio(false);

        List<String> params = getParameters().getRaw();
        if(params.size() == 2) {
            mediaView.setFitWidth(Integer.parseInt(params.get(0)));
            mediaView.setFitHeight(Integer.parseInt(params.get(1)));
        } else {
            mediaView.setFitWidth(1280);
            mediaView.setFitHeight(720);
        }

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                primaryStage.close();
            }
        });

        primaryStage.setScene(new Scene(new FlowPane(mediaView)));
        primaryStage.show();
    }
}
