/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
//import gnu.io.SerialPortEvent;
//import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.HeadlessException;
//import java.awt.Insets;
import static java.awt.image.ImageObserver.ERROR;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;
import javax.swing.JOptionPane;
//import javax.swing.JSlider;
import java.util.List;

/**
 *
 * @author pablinux
 */
public class Comunicacion_Arduino {

    boolean serial = true;
    SerialPort serialPort;
    private String NOM_PUERTO = "";
    private String PARIDAD;
    private String DATABITS;
    public int TIME_OUT = 2000;
    public int DATA_RATE = 9600;

    CommPortIdentifier portId;
    Enumeration enumPuertos;
    //**********variable control lectura datos*********
    boolean continuar = false;//control de hilo de comunicacion serial
    private boolean conStad = false;//comprueba el estado de conexion
    Thread tLectura;
    boolean auto = false;//para borrar el bufer de lectura
    String msg = "";//mensaje recibido del serial 

    private OutputStream salDat = null;
    private InputStream entDat = null;
    
    ///*******VARIABLE CONTROL ERRORES********///
    List listaErrores;
    ///*************VARIABLES PARA CONEXION RED***************///
    boolean eth = false;
    String ip = "192.168.10.210";
    int puerto = 1803;
    Socket soketClient;
    ///************control de info
    String selSensor = "";//mensaje recibido del serial 
    boolean modoAuto = false;
    int tiempoLecturaSensores = 2000;//tiempo en que lee los sensores
    //*******lectura sensores*****
    boolean lectSens = false;

    //*****************Serial Comunicacion ***********
    List listaPuertos;

    public void buscarPuertos() {
        enumPuertos = CommPortIdentifier.getPortIdentifiers();
        listaPuertos.clear();
        while (enumPuertos.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) enumPuertos.nextElement();
            //System.out.println(currPortId.getName());
            listaPuertos.add(currPortId.getName());//agregamos los puertos
        }
    }

    public void setPuertoSerial(String IdPuerto) {
        NOM_PUERTO = IdPuerto;
    }

    public void ConexionSerial() {
        enumPuertos = CommPortIdentifier.getPortIdentifiers();
        while (enumPuertos.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) enumPuertos.nextElement();
            if (NOM_PUERTO.equals(currPortId.getName())) {
                portId = currPortId;
                //
                System.out.println(NOM_PUERTO);
                break;
            }
        }
        if (portId == null) {
            System.exit(ERROR);
            return;
        }
        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        } catch (PortInUseException | UnsupportedCommOperationException e) {
            System.out.println(e);
            System.exit(ERROR);
        }
        //iniciada conexion
        conStad = true;
    }
//***envio datos por serial

    public void EnviarDatosSerial(String data) {
        if (conStad == false) {
            String err = "debe conectarse primero";
            listaErrores.add(err);
        } else {
            try {
                if (eth == true) {
                    salDat = new DataOutputStream(soketClient.getOutputStream());
                    salDat.write(data.getBytes());
                }
                if (serial == true) {
                    salDat = serialPort.getOutputStream();
                    salDat.write(data.getBytes());
                }

            } catch (IOException e) {
                System.out.println(e);
                System.exit(ERROR);
            }
        }
        //resetDat=true;
    }

    public void EnviarDatosEth(String data) {
        String datb = " " + data;
        if (conStad == false) {
            listaErrores.add("Debe conectar primero");
        } else {
            try {
                salDat = new DataOutputStream(soketClient.getOutputStream());
                salDat.write(datb.getBytes());
            } catch (IOException e) {
                System.out.println(e);
                System.exit(ERROR);
            }
        }
        //resetDat=true;
    }
    
    
//lectura de puerto con thread
    Thread t;

    public void recibirDatos() {
        try {
            t = new Thread(new hiloLeeSerial()); //creamos el hilo
            if (eth == true) {
                entDat = soketClient.getInputStream();//esta variable del tipo InputStream obtiene el dato de red
            }
            if (serial == true) {
                entDat = serialPort.getInputStream();//esta variable del tipo InputStream obtiene el dato serial
            }
            t.start();//iniciamos el hilo 
        } catch (IOException | HeadlessException e) {
            System.out.println(e);
        }
    }
    public class hiloLeeSerial implements Runnable {
        //private boolean continuar = true;   
        //public void pararHilo(){
        //continuar = false;

        char c = ' ';
        private String datRecb = "";
        int aux;

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            while (continuar) {//si continuar es true lee datos
                try {
                    aux = entDat.read(); // aqui estamos obteniendo nuestro dato serial
                    Thread.sleep(5);
                    if (aux > 0) {
                        //System.out.println((char)aux);//imprimimos el dato serial
                        c = ((char) aux);
                        //datRecb = datRecb +(c);
                        //msg = msg +((char)aux);
                        //msg =datRecb;
                        //Write(datRecb);
                        if (c == '*') {
                            msg = datRecb;
                            datRecb = "";
                            c = ' ';
                            System.out.println(c);//
                            System.out.println(msg);
                            Write(msg);
                            //setLecturaSensores(msg);
                        }
                        datRecb = datRecb + (c);
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
// control de datos serial
    String recepDatos;
    public void Write(String dat) {
        recepDatos = dat;
        System.out.println("Lectura: "+dat);
    }
}
