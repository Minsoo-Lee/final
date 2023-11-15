package application;

import javax.swing.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class web extends JFrame {
    WebView webView = new WebView();
    public static void initAndLoadWebView(final JFXPanel fxPanel) {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);

        WebView webView = new WebView();

        group.getChildren().add(webView);
        webView.setMinSize(630, 830);
        webView.setMaxSize(630, 830);

        WebEngine webEngine = webView.getEngine();

        webEngine.load("https://map.naver.com/v5/");


    }
    public void goBack() {
        WebEngine webEngine = webView.getEngine();
        Platform.runLater(() -> {
            webEngine.executeScript("history.back()");
        });
    }

    public void goForward() {
        WebEngine webEngine = webView.getEngine();
        Platform.runLater(() -> {
            webEngine.executeScript("history.forward()");
        });
    }

}