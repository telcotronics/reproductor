/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorWeb;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import control.Monitor_control;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import okhttp3.HttpUrl;
import video.PanelYouTube;

/**
 *
 * @author pablinux
 */
public class ServidorHttp{
    
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
    public String verIpServer(){
        return httpd.getAddress().toString();
    }
    
    
    public void serverConfig(int puerto) throws IOException{
        
        httpd = HttpServer.create(new InetSocketAddress(puerto),0);
        HttpContext ctxInit = httpd.createContext("/");
        HttpContext ctxCmd = httpd.createContext("/cmd");
        HttpContext ctxCtrl = httpd.createContext("/ctrl");
        HttpContext ctxInfo = httpd.createContext("/info");
        HttpContext ctxCss = httpd.createContext("/css/audio_control.css");//audio_control.css
        //GENERA GETS
        ctxInit.setHandler(ServidorHttp::solicitudInit);
        ctxCmd.setHandler(ServidorHttp::solicitudCmd);
        ctxCtrl.setHandler(ServidorHttp::solicitudCtrl);
        ctxInfo.setHandler(ServidorHttp::solicitudInfo);
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
        //Monitor_control mon = new Monitor_control();
        final int CODIGO_RESPUESTA = 200;
        String filtraDat = filtrarSolicitud(exchange, "accion");//filtra origen del commando
        System.out.println("Resultado filtro: "+filtraDat);
        switch(filtraDat){
            case "reproducir" : accion="reproducir";
                break;
            case "pausar" : accion="pausar";
                break;
            case "parar" : accion="parar";
                break;
            case "adelante" : 
                break;
            case "atras" : 
                break;
            case "verPanel" : 
                break;
            case "ocultaPanel" : 
                break;
            case "redimensiona" : 
                break;
            
            case "ver_videoOn" : accion="ver_videoOn";
                break;
            case "ver_videoOf" : accion="ver_videoOf";
                break;
            case "panel_listRepOn" : accion="panel_listaRepOn";
                break;
            case "panel_listRepOf" : accion="panel_listaRepOf";
                break;
            case "video_fullScreenOn" : accion="fullScreenOn";
                break;
            case "video_fullScreenOf" : accion="fullScreenOf";
                break;
            case "play_video" : accion="play_video";
                break;
            case "pausa_video" : accion="pausa_video";
                break;
            case "stop_video" : accion="stop_video";
                break;
            case "next_video" : accion="siguiente_video";
                break;
            case "prev_video" : accion="anterior_video";
                break;
            
            case "update_list" : accion="actualiza_lista";
                break;
        }
        System.out.println("ServidorHttp: "+accion);
        String contenido = enviaJson("cmd",filtraDat);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    public static void solicitudCtrl(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        String filtraDat = filtrarSolicitud(exchange, "ctrl");//origen del ctrl
        String contenido = enviaJson("ctrl",filtraDat);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    public static void solicitudInfo(HttpExchange exchange) throws IOException{
        final int CODIGO_RESPUESTA = 200;
        String filtro = filtrarSolicitud(exchange, "info");
        String contenido = enviaJson("info",filtro);
        
        exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(contenido.getBytes());
        os.close();
    }
    
    private static String filtrarSolicitud(HttpExchange exchange,String parametNombreBuscar){
        String requestURL = "http://"+exchange.getRequestHeaders().getFirst("Host")+exchange.getRequestURI();
        String valor="";
        System.out.println("\nEncabezado:");
        exchange.getResponseHeaders().entrySet().forEach(System.out::println);
        
        System.out.println("\nMetodo: "+exchange.getRequestMethod());
//        System.out.println("\nConsulta:");
        
        URI uri = exchange.getRequestURI();
        System.out.print(uri.getQuery());
        
        HttpUrl url = HttpUrl.parse(requestURL);
        if(url != null){
            for(int x=0; x<url.querySize(); x++){
                //System.out.printf("%s: %s",url.queryParameterName(x),url.queryParameterValue(x));
                System.out.println("\nNombre:"+url.queryParameterName(x));
                System.out.println("Valor:"+url.queryParameterValue(x));
                if(parametNombreBuscar.equals(x)){//filtramos el parametro hasta encontrarlo
                    System.out.println(""+url.queryParameterValue(x));
                }
                valor = url.queryParameterValue(x);
            }
        }
        return valor;
    }
    
    public static String enviaJson(String nombre,String filtro){
        String json2 = "{\"Items\":[{\"codigo_prdcto\":\"TTU00348A1\",\"detalle_prdcto\":\"NANO STATION  LOCO M2\",\"describe_prdcto\":\"ANTENA NANO STATION BLANCA 2.4 GHZ,8\"}";
        String json = "{\"nombre\": \""+nombre+"\", \"ACCION\":"+filtro+"}";
        return json;
    }

    static String accion="";
    public String acciones_leer(){
        String cmd = accion;
        return cmd;
    }
    public void acciones_limpia(){
        accion="";
    }
//    @Override
//    public boolean panelYt_init(boolean STD) {
//        return super.panelYt_init(STD); //To change body of generated methods, choose Tools | Templates.
//    }
    
    
    
    /**********CONTROL***********/
//    PanelYouTube pyt;
//    boolean stdYt=false;
//    public boolean panelYt_iniciar(boolean STD){
//        System.out.println(pyt.isActive());
//        if(!stdYt){
//            pyt.setVisible(STD);
//        }else{
//            pyt.setVisible(STD);
//        }
//        return pyt.isActive();
//    }
//    public boolean panelYt_verPnl(boolean STD){
//        pyt.verPanel(STD);
//        System.out.println(pyt.getState());
//        return true;
//    }
//    public void panelYt_full(boolean pantComplt){
//        //pyt.setUndecorated(pantComplt);
//        pyt.fullScreen(pantComplt);
//    }
    
    
    
    /*PRUEBAS*/
    
    
    
}
