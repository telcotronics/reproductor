/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author pablinux
 */
public class Obj_estadRep {
    String tema_actual,estadoRep;
    long tiempo;
    boolean est_panel,est_pantalla,estado_lista;

    public String getTema_actual() {
        return tema_actual;
    }

    public void setTema_actual(String tema_actual) {
        this.tema_actual = tema_actual;
    }

    public String getEstadoRep() {
        return estadoRep;
    }

    public void setEstadoRep(String estadoRep) {
        this.estadoRep = estadoRep;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isEst_panel() {
        return est_panel;
    }

    public void setEst_panel(boolean est_panel) {
        this.est_panel = est_panel;
    }

    public boolean isEst_pantalla() {
        return est_pantalla;
    }

    public void setEst_pantalla(boolean est_pantalla) {
        this.est_pantalla = est_pantalla;
    }

    public boolean isEstado_lista() {
        return estado_lista;
    }

    public void setEstado_lista(boolean estado_lista) {
        this.estado_lista = estado_lista;
    }

    
}
