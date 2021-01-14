/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
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
        if(!stdYt){
            pyt.setVisible(STD);
        }else{
            pyt.setVisible(STD);
        }
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
        //pyt.setUndecorated(pantComplt);
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
                //System.out.println("Leyendo Servidor: "+cmd);
                server.acciones_limpia();
            } catch (Exception e) {
                System.out.println("produccionE_inventario.Inventario.Proceso2.run()"+e.getMessage());
            }
        }
        
    }
    private void accionesRemotas(String cmd){
        switch(cmd){
            case "reproducir" : 
                break;
            case "pausar" : 
                break;
            case "parar" : 
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
            case "fullScreen" : 
                break;
            case "ver_video" : panelYt_init(true);
                break;
            case "play_video" : panelYt_play();
                break;
        }
    }
}
