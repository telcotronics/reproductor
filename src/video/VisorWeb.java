package video;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLTextAreaElement;
import org.w3c.dom.html.HTMLInputElement;
/**
 *
 * @author pablinux
 */
public class VisorWeb extends JFXPanel{
    private WebEngine engine;

    //Constructor de la clase
    public VisorWeb() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebView view = new WebView();
                engine = view.getEngine();
                engine.setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36");
                engine.setJavaScriptEnabled(true);
                //engine.executeScript("");
                //engine.getDocument().getElementById("usuario");//.setNodeValue("TestMode");

                setScene(new Scene(view));
            }
        });
        setVisible(true);
    }

//Método para cargar la URL de la página web
    public void loadURL(final String url,String user,String pasw) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String dirUrl = toURL(url);
                if (dirUrl == null) {
                    dirUrl = toURL(url);
                }
                engine.load(dirUrl);

                engine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State t, State t1) -> {
                    System.out.println("Usuario Add: " + State.SUCCEEDED);
                    Document document = engine.getDocument();
                    Element elementUsr = document.getElementById("usuario");
                    HTMLInputElement usr = (HTMLInputElement) elementUsr;
                    usr.setValue(user);
                    
                    Element elementPwd = document.getElementById("password");
                    HTMLInputElement pwd = (HTMLInputElement) elementPwd;
                    pwd.setValue(pasw);
                    System.out.println("User: "+user);
                    System.out.println("User: "+pasw);
                });
            }
        });
    }

    private String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }

    ///********** Metodos prueba **********////
    public void verUser() {
        if (engine != null) {
            Document document = engine.getDocument();
            if (document != null) {
                Element element = document.getElementById("usuario");
                if (element instanceof HTMLTextAreaElement) {
                    HTMLTextAreaElement textArea = (HTMLTextAreaElement) element;
                    String text = textArea.getValue();
                    if (text != null && !text.isEmpty()) {
                        System.out.println("BINGO:\n" + text);
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void addJs() {
        engine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> ov, State t, State t1) -> {
            System.out.println("Usuario Add: " + State.SUCCEEDED);
            Document document = engine.getDocument();
            Element element = document.getElementById("usuario");
            HTMLInputElement txt = (HTMLInputElement) element;
            String dat = txt.getValue();
            System.out.println("User: " + dat);
        });
    }

    public void exeJS() {
        engine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                engine.executeScript("verId()");
            }
        });
//        Document document = engine.getDocument();
//        Element element = document.getElementById("usuario");
//        HTMLInputElement txt = (HTMLInputElement) element;
//        txt.setValue("TestMode");
    }
}
