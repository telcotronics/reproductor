/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author pablinux
 */
public class Prueba extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://xsystem.ddns.net/app/rep_audio/");

        //root.getChildren().add(webView);


        
        
        Button btnPlay = new Button();
        btnPlay.setText("Play");
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        Button btnNext = new Button();
        btnNext.setText("Siguiente");
        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        Button btnStop = new Button();
        btnStop.setText("Stop");
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().addAll(webView,btnNext,btnStop,btnPlay);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Reproductor Audio");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
