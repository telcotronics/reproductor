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
    boolean est_panelvideo,est_pantalla,estado_lista;
    int nivel_vol;
    long tiempo;
    String id_tema,tema_actual,estadoRep;

    public String getTema_actual() {
        return tema_actual;
    }

    public void setTema_actual(String tema_actual) {
        this.tema_actual = tema_actual;
    }

    public int getNivel_vol() {
        return nivel_vol;
    }

    public void setNivel_vol(int nivel_vol) {
        this.nivel_vol = nivel_vol;
    }

    public String getId_tema() {
        return id_tema;
    }

    public void setId_tema(String id_tema) {
        this.id_tema = id_tema;
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

    public boolean isEst_panelvideo() {
        return est_panelvideo;
    }

    public void setEst_panelvideo(boolean est_panelvideo) {
        this.est_panelvideo = est_panelvideo;
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
