/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorWeb;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 *
 * @author pablinux
 */
public class ServidorHttp {
    
    HttpServer httpd;
    boolean stdServer;
    
    public String iniciarServidor(){
        if(!stdServer){
            httpd.start();
            stdServer = true;
        }
        return "\nServidor Iniciado..";
    }
    
    public String pararServidor(){
        if(stdServer){
            httpd.stop(0);
            stdServer=false;
        }
        return "\nServidor detenido..";
    }
    
    public boolean verStadoServer(){
        return stdServer;
    }
    
    public void serverConfig(int puerto) throws IOException{
        
        httpd = HttpServer.create(new InetSocketAddress(puerto),0);
        HttpContext ctxInit = httpd.createContext("/");
        HttpContext ctxCmd = httpd.createContext("/cmd");
        HttpContext ctxCtrl = httpd.createContext("/Ctrl");
        HttpContext ctxCss = httpd.createContext("/css/audio_control.css");//audio_control.css
        //GENERA GETS
        ctxInit.setHandler(ServidorHttp::solicitudInit);
        ctxCmd.setHandler(ServidorHttp::solicitudCmd);
        ctxCtrl.setHandler(ServidorHttp::solicitudCtrl);
        ctxCss.setHandler(ServidorHttp::solicitudCss);
    }

    
    public static void solicitudInit(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        ControlWeb web = new ControlWeb();
        String contenido = web.initWeb();
        
        exchange.getResponseHeaders().set("Content-Type", "text/html, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    public static void solicitudCss(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        ControlWeb web = new ControlWeb();
        String contenido = web.css();
        
        exchange.getResponseHeaders().set("Content-Type", "text/html, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    
    public static void solicitudCmd(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        String contenido = enviaJson();
        
        exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    public static void solicitudCtrl(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        String contenido = enviaJson();
        
        exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    public static void solicitudTest(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        String contenido = enviaJson();
        
        exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    
    public static String enviaJson(){
        String json2 = "{\"Items\":[{\"codigo_prdcto\":\"TTU00348A1\",\"detalle_prdcto\":\"NANO STATION  LOCO M2\",\"describe_prdcto\":\"ANTENA NANO STATION BLANCA 2.4 GHZ,8\"}";
        String json = "{\"nombre\": \"John\", \"edad\":21}";
        return json2;
    }
    
}
