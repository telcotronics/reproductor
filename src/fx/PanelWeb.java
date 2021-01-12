/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author pablinux
 */
public class PanelWeb extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            Button btn = new Button();
            btn.setText("Say 'Hello World'");
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Hello World!");
                }
            });
            Parent root = FXMLLoader.load(getClass().getResource("Ventana.fxml"));
            //root.getChildrenUnmodifiable().add(btn);
            Scene ecena = new Scene(root);
            primaryStage.setTitle("Bienvenido");
            primaryStage.setScene(ecena);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
