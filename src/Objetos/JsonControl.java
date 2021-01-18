/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pablinux
 */
public class JsonControl {
    
    String StringJson;
    JsonParser parser = new JsonParser();
    JsonElement datos;

    public JsonControl(){
    }
    
    public JsonControl(String StringJson) {
        this.StringJson = StringJson;
        this.datos = parser.parse(StringJson);
    }
    
    public JsonElement convJsonElement(String Json){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(Json);
        return jsonElement;
    }
    
    public String getElementArray(String Json,int fila, String NombElemento){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(Json);
        JsonArray array = jsonElement.getAsJsonArray();
        //array.get(0).getAsString();
        //System.out.println(array.get(0));
        JsonObject object = array.get(fila).getAsJsonObject();
        //object.get("name");
        //System.out.println(object.get(NombElemento));
        return object.get(NombElemento).toString();
    }
    public String getValorJsonElement(JsonElement json, String NombElemento){
        //array.get(0).getAsString();
        //System.out.println(array.get(0));
        JsonObject object = json.getAsJsonObject();
        //object.get("name");
        //System.out.println(object.get(NombElemento));
        return object.get(NombElemento).toString();
    }
    public String getElementObject(JsonObject jsonObj, String NombElemento){
        //jsonObj.get(NombElemento);
        //System.out.println(object.get(NombElemento));
        return jsonObj.get(NombElemento).toString();
    }
    
    public String verTipoJsonString(String tipoJson) throws Exception{
        datos = parser.parse(tipoJson);
        JsonElement elemento = datos;
        if(elemento.isJsonObject()){
            return "Es objeto";
        }else if(elemento.isJsonArray()){
            JsonArray array = elemento.getAsJsonArray();
            return "Es array. Numero de elementos: " + array.size();
        }else if(elemento.isJsonPrimitive()){
            return "Es primitiva";
        }else if(elemento.isJsonNull()){
            return "Es null";
        }else{
            return "Es otraCosa";
        }
    }
    
    public String verTipoJson_element(JsonElement elemento){
        if(elemento.isJsonObject()){
            return "Es objeto";
        }else if(elemento.isJsonArray()){
            JsonArray array = elemento.getAsJsonArray();
            return "Es array. Numero de elementos: " + array.size();
        }else if(elemento.isJsonPrimitive()){
            return "Es primitiva";
        }else if(elemento.isJsonNull()){
            return "Es null";
        }else{
            return "Es otraCosa";
        }
    }
    
    public Iterator JsonElement(JsonElement elemento){
        //Map.Entry<String,JsonElement> entrada;
        JsonObject obj = elemento.getAsJsonObject();
        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
        while (iter.hasNext()) {
            java.util.Map.Entry<String,JsonElement> entrada = iter.next();
            //entrada = iter.next();
            System.out.println("Clave: " + entrada.getKey());
            System.out.println("Valor:"+entrada.getValue());
            //dumpElement(entrada.getValue());
        }
        return iter;
    }
    
    
    public int contArrayElemnt(JsonElement elemento){
       JsonArray array = elemento.getAsJsonArray();
       return array.size();
    }
    
    public JsonArray convJsonElemnt_Array(JsonElement elemento){
       JsonArray array = elemento.getAsJsonArray();
       return array;
    }
    public Iterator convElemnt_ArrayToIterator(JsonElement elemento){
       JsonArray array = elemento.getAsJsonArray();
       java.util.Iterator<JsonElement> iter = array.iterator();
       /*while (iter.hasNext()) {
           JsonElement entrada = iter.next();
           System.out.println(entrada.toString());
       }*/
       return iter;
    }
    
    
    
    
    ///*********** TESTING  *************///
    public void pruebaJsonElement(JsonElement elemento){
        //Map.Entry<String,JsonElement> entrada;
        JsonObject obj = elemento.getAsJsonObject();
        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
        while (iter.hasNext()) {
            java.util.Map.Entry<String,JsonElement> entrada = iter.next();
            System.out.println("Clave: " + entrada.getKey());
            System.out.println("Valor:"+entrada.getValue());
        }
    }
    public String pruebaArrayElemnt(JsonElement elemento){
       String cantJsonArray="\n";
       JsonArray array = elemento.getAsJsonArray();
       java.util.Iterator<JsonElement> iter = array.iterator();
       while (iter.hasNext()) {
           JsonElement entrada = iter.next();
           System.out.println(entrada.toString());
           cantJsonArray += entrada.toString();
       }
       return cantJsonArray;
    }
    public void pruebaArrayElemntConsole(JsonElement elemento){
       JsonArray array = elemento.getAsJsonArray();
       java.util.Iterator<JsonElement> iter = array.iterator();
       while (iter.hasNext()) {
           JsonElement entrada = iter.next();
           System.out.println(entrada.toString());
       }
    }
    
    public String testTipoJson(String Json){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(Json);
        return dumpElement(jsonElement);
    }
    
    public String dumpElement(JsonElement elemento){
        String tipoJson;
        if(elemento.isJsonObject()){
            tipoJson="Es objeto";
            JsonObject obj = elemento.getAsJsonObject();
            java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
            java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
            while (iter.hasNext()) {
                java.util.Map.Entry<String,JsonElement> entrada = iter.next();
                System.out.println("Clave: " + entrada.getKey());
                System.out.println("Valor:");
                dumpElement(entrada.getValue());
            }
        }else if(elemento.isJsonArray()){
            JsonArray array = elemento.getAsJsonArray();
            tipoJson="Es array. Numero de elementos: " + array.size();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                dumpElement(entrada);
            }
        }else if(elemento.isJsonPrimitive()){
            tipoJson="Es primitiva";
            JsonPrimitive valor = elemento.getAsJsonPrimitive();
            if (valor.isBoolean()) {
                System.out.println("Es booleano: " + valor.getAsBoolean());
            } else if (valor.isNumber()) {
                System.out.println("Es numero: " + valor.getAsNumber());
            } else if (valor.isString()) {
                System.out.println("Es texto: " + valor.getAsString());
            }
        }else if(elemento.isJsonNull()){
            tipoJson="Es null";
        }else{
            tipoJson="Es otraCosa";
        }
        return tipoJson;
    }

}
