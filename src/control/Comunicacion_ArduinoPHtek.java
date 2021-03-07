/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.ArrayList;
import java.util.List;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author pablinux
 */
public class Comunicacion_ArduinoPHtek {
    //*COMUNCIACION SERIAL ARDUINO*//
    String info;
    private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    
    
    private final SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (ino.isMessageAvailable()) {
                    info = "Resultado: " + ino.printMessage();
                }
            } catch (SerialPortException | ArduinoException ex) {
                setError(".serialEvent()"+ex.getMessage(),"SerialPortEventListener");
            }
        }
    };
    
    public List verificaPuertos(){
        List puertos =new ArrayList();
        try {
            if(ino.getPortsAvailable()>0){
                puertos = ino.getSerialPorts();
            }
            System.out.println(ino.getPortsAvailable());
        } catch (Exception e) {
            setError(e.getMessage(),"verificaPuertos()");
            puertos.add("No Hay Puertos Disponibles");
        }
        return puertos;
    }
    
    public boolean initComunicacion(String Puerto,int baudRate,SerialPortEventListener eschPort){
        try {
            ino.arduinoRXTX(Puerto, baudRate, listener);
            return true;
        } catch (ArduinoException ex) {
            setError("Error en: "+ex.getMessage(),"initComunicacion(Puerto,,b,s)");
            return false;
        }
    }
    
    public boolean finComunicacion(){
        try {
            ino.killArduinoConnection();
            return true;
        } catch (ArduinoException e) {
            setError(e.getMessage(),"finComunicacion");
            return false;
        }
    }
    
    
    public void envioDato(String dato){
        try {
            ino.sendData(dato);
        } catch (ArduinoException | SerialPortException e) {
            e.printStackTrace();
            setError(e.getMessage(),"envioDato");
        }
    }
    
    public SerialPortEventListener getListener(){
        return listener;
    }
    
    List errorList = new ArrayList();
    private void setError(String error,String function){
        error+="\nControl.Comunicacion_ArduinoPHtek."+function;
        errorList.add(0, error);
        System.out.println(error);
    }
    public List leeError(){
        return errorList;
    }
    public void clearErrores(){
        errorList.clear();
    }
}
