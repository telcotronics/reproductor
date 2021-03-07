/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Objetos.Obj_listRepYT;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jssc.SerialPortEventListener;
import reproductor_de_musica.Ventana_principal;
import servidorWeb.ServidorHttp;
import video.PanelYouTube;

/**
 *
 * @author pablinux
 */
public class Monitor_control {
    //Monitor_control mon = new Monitor_control();
    Ventana_principal mp3;
    PanelYouTube pyt;
    ServidorHttp web;
    ServidorHttp server = new ServidorHttp();
    Comunicacion_ArduinoPHtek com = new Comunicacion_ArduinoPHtek();
    public boolean init_panel(){
        mp3 = new Ventana_principal();
        pyt = new PanelYouTube();
        web = new ServidorHttp();
        reloj();
        return true;
    }
    
    
    boolean std_web = false;
    public String initServerWeb(Component parent){
        String msg;
        try {
            if(!std_web){
                server.serverConfig(1303);
                msg = server.iniciarServidor();
                System.out.println(server.verIpServer());
                msg+= "\nlocalhost:1303";
                std_web = false;
            }else{
                msg = server.pararServidor();
                std_web = true;
            }
        } catch (IOException e) {
            //JOptionPane.showMessageDialog(parent, e.getMessage());
            msg = e.getMessage();
        }
        return msg;
    }
    
    boolean stdYt=false;
    public boolean panelYt_init(boolean STD){
        System.out.println(pyt.isActive());
        pyt.setVisible(STD);
        return pyt.isActive();
    }
    public boolean panelYt_verPnl(boolean STD){
        pyt.verPanel(STD);
        System.out.println(pyt.getState());
        return true;
    }
    public void panelYt_full(boolean pantComplt){
        //pyt.setUndecorated(pantComplt);
        pyt.fullScreen(pantComplt);
    }
    public void panelYt_play(){
        pyt.PLAY();
    }
    public void panelYt_stop(){
        pyt.STOP();
    }
    public void panelYt_pause(){
        pyt.PAUSE(true);
    }
    public void panelYt_siguiente(){
        pyt.SIGUIENTE();
    }
    public void panelYt_anterior(){
        pyt.ANTERIOR();
    }
    public void panelYt_actLista(){
        pyt.ACT_LISTA();
    }
    public void panelMp3_play(){
        pyt.PLAY();
    }
    
    int refrescarDat = 0;
    public void reloj() {
        //Calendar calendario = new java.util.GregorianCalendar();
        //int segundos = calendario.get(Calendar.SECOND);
        //javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
        //String h="";
        Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if(refrescarDat==3){
                    //Genera un hilo de proceso
                    Thread hilo = new Thread(new ThreadEscucharServWeb());//**hilo publicar items**//
                    hilo.start();
                    refrescarDat=0;
                }refrescarDat++;
            }
        });
        timer.start();
    }
    
    //**hilo publicar items**//
    public class ThreadEscucharServWeb implements Runnable{
        @Override
        public void run() {
            try {
                String cmd = server.acciones_leer();
                accionesRemotas(cmd);
//                System.out.println("Leyendo Servidor: "+cmd);
                server.acciones_limpia();
                addListRep();//agrega lista re reproduccion
                addStatusRep();//lee estado de reproduccion
                nivel_vol();//lee el nivel de audio
            } catch (Exception e) {
                System.out.println("Error en: control.Monitor_control.ThreadEscucharServWeb.run()\n"+e.getMessage());
            }
        }
    }
    
    public void envioDato(String cmd){
        accionesRemotas(cmd);
    }
    String[] mode = {"youtube","mp3"};
    private void accionesRemotas(String cmd){
        
        switch(cmd){
            case "reproducir" : panelMp3_play();
                break;
            case "pausar" : 
                break;
            case "parar" : 
                break;
            case "adelante" : 
                break;
            case "atras" : 
                break;
            case "panel_listaRepOn" : panelYt_verPnl(true);
                break;
            case "panel_listaRepOf" : panelYt_verPnl(false);
                break;
            case "fullScreenOn" :panelYt_full(true); 
                break;
            case "fullScreenOf" :panelYt_full(false); 
                break;
            case "ver_videoOn" : panelYt_init(true);
                break;
            case "ver_videoOf" : panelYt_init(false);
                break;
            case "play_video" : panelYt_play();
                break;
            case "pausa_video" : panelYt_pause();
                break;
            case "stop_video" : panelYt_stop();
                break;
            case "siguiente_video" : panelYt_siguiente();
                break;
            case "anterior_video" : panelYt_anterior();
                break;
            case "actualiza_lista" : panelYt_actLista();
                break;
            
            case "ctrl_iniSerial" : initConexionSerial();
                break;
            case "ctrl_finSerial" : cierraConexionSerial();
                break;
            case "ctrl_p1=1" : com.envioDato("A");
                break;
            case "ctrl_p1=0" : com.envioDato("a");
                break;
            case "ctrl_p2=1" : com.envioDato("B");
                break;
            case "ctrl_p2=0" : com.envioDato("b");
                break;
            case "ctrl_p3=1" : com.envioDato("C");
                break;
            case "ctrl_p3=0" : com.envioDato("c");
                break;
            case "ctrl_p4=1" : com.envioDato("D");
                break;
            case "ctrl_p4=0" : com.envioDato("d");
                break;
            case "ctrl_p5=1" : com.envioDato("E");
                break;
            case "ctrl_p5=0" : com.envioDato("e");
                break;
            case "ctrl_p6=1" : com.envioDato("F");
                break;
            case "ctrl_p6=0" : com.envioDato("f");
                break;
            case "ctrl_p7=1" : com.envioDato("G");
                break;
            case "ctrl_p7=0" : com.envioDato("g");
                break;
            case "ctrl_p8=1" : com.envioDato("H");
                break;
            case "ctrl_p8=0" : com.envioDato("h");
                break;
        }
        if(cmd.contains("ctrl_port")){
            String port = cmd.substring(10, cmd.length());
            System.out.println("El Puerto es: "+port);
            setPuerto(port);
        }
    }
    //Obj_listRepYT obj = new Obj_listRepYT();
    List lista_repYt;
    private void addListRep(){
        if(server.verListaRep()!=null){
            lista_repYt = server.verListaRep();
//            System.out.println("Tiene Datos");
            pyt.INGRESA_LISTA(lista_repYt);
            server.cleanListaRep();
        }
    }
    
    private void addStatusRep(){
        server.estadoReproductor(pyt.leer_estadoRep());
    }
    
    private void nivel_vol(){
        if(server.isControlVolumen()){
            System.out.println("Nivel Audio:"+server.nivel_vol());
            pyt.setNivelVol(server.nivel_vol());
        }
    }
    private void leeErrores(){
        
    }
    
    //****CONTROL ARDUINO****//
    String puerto;
    private void setPuerto(String NomPuerto){
        puerto = NomPuerto;
    }
    private void initConexionSerial(){
        System.out.println("Inicado Conexion: "+puerto);
        com.initComunicacion(puerto, 9600, com.getListener());
    }
    public List ListaPuertos(){
        List puertos =new ArrayList();
        puertos=com.verificaPuertos();
        server.setListaPuertos(puertos);
        return puertos;
    }
    public boolean cierraConexionSerial(){
        return com.finComunicacion();
    }
    
}
