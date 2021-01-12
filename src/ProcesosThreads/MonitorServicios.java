/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcesosThreads;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Timer;

/**
 *
 * @author pablinux
 */
public class MonitorServicios{
    
    ResultSet verSql;
    int refrescarDat = 0;
    //parametro de configuracion
    int tiempoRefresco=60;
    
    int fallosPub =0;
    
    public boolean recepcionAcciones(){
        
        return true;
    }
    
    private void cargaParametros(){
        tiempoRefresco = 20;
    }
    
    public void reloj() {
        cargaParametros();
        //Calendar calendario = new java.util.GregorianCalendar();
        //int segundos = calendario.get(Calendar.SECOND);
        //javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
        //String h="";
        Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if(refrescarDat==tiempoRefresco){
                    //Genera un hilo de proceso
                    Thread hilo = new Thread(new ThreadPublicaItems());//**hilo publicar items**//
                    hilo.start();
                    refrescarDat=0;
                }refrescarDat++;
            }
        });
        timer.start();
    }
    
    //**hilo publicar items**//
    public class ThreadPublicaItems implements Runnable{
        @Override
        public void run() {
            try {
                recepcionAcciones();
                //System.out.println("Fin Hilo");
            } catch (Exception e) {
                System.out.println("produccionE_inventario.Inventario.Proceso2.run()"+e.getMessage());
            }
        }
        
    }
}
