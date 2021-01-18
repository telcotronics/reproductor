/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Objetos.Obj_listRepYT;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import objetos.JsonControl;

/**
 *
 * @author pablinux
 */
public class Extractor_json {

    JsonControl jsonCtrl = new JsonControl();
    
    public List extraerListaRepYt(String datConsulta) {
        List<Object> lista = new ArrayList();
        Obj_listRepYT obj_yt;// = new Obj_listRepYT();
        JsonArray array = jsonCtrl.convJsonElemnt_Array(jsonCtrl.convJsonElement(datConsulta));
        //System.out.println("Iterando: "+array);
        Iterator<JsonElement> iter = array.iterator();
        String id, nom;
        while (iter.hasNext()) {
            obj_yt = new Obj_listRepYT();
            JsonElement entrada = iter.next();
            //Analizamos Los elementos
            JsonObject jsonObjectItems = new JsonParser().parse(entrada.toString()).getAsJsonObject();
            id = jsonObjectItems.get("id").getAsString();
            nom = jsonObjectItems.get("nombre").getAsString();
            obj_yt.setId(id);
            obj_yt.setNombre(nom);
            //System.out.println("Estractor Json: "+obj_yt.getId() + " " + obj_yt.getNombre());
            lista.add(obj_yt);
        }
        return lista;
    }
}
