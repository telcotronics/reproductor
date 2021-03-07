/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorWeb;

/**
 *
 * @author pablinux
 */
public class ControlWeb {

    String web = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "\n"
            + "<head>\n"
            + "  <meta charset=\"UTF-8\">\n"
            + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "  <title>Control AUDIO</title>\n"
            + "  \n"
            + "  <!-- Compiled and minified JavaScript -->\n"
            + "  <script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-3.5.0.min.js\"></script>\n"
            + "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js\"></script>\n"
            + "  \n"
            + "  <!-- Compiled and minified CSS -->\n"
            + "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css\">\n"
            + "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css\">\n"
            + "\n"
            + "  <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n"
            + "  <link rel=\"stylesheet\" href=\"https://telcotronics.com/css/audio_control.css\">\n"
            + "</head>\n"
            + "\n"
            + "<body onLoad=\"controlSincJson()\">\n"
            + "  <header></header>\n"
            + "  <div class=\"container\">\n"
            + "    <div class=\"ctrl_display\">\n"
            + "    <div id=\"\" class=\"bt_flotanteBack\" onclick=\"back()\"><i class=\"material-icons left btn_dysplay\">fast_rewind</i></div>\n"
            + "    <div id=\"bt_flotantePlay\" class=\"bt_flotantePlay\" onclick=\"play()\"><i class=\"material-icons left btn_dysplay\">play_arrow</i></div>\n"
            + "    <div id=\"\" class=\"bt_flotanteStop\" onclick=\"stop()\"><i class=\"material-icons left btn_dysplay\">stop</i></div>\n"
            + "    <div id=\"\" class=\"bt_flotanteNext\" onclick=\"next()\"><i class=\"material-icons left btn_dysplay\">fast_forward</i></div>\n"
            + "    <div id=\"\" class=\"bt_flotanteVol\"><p class=\"range-field\"><span> Volumen: </span><input type=\"range\" id=\"ctrl_vol\" min=\"0\" max=\"100\"/></div></p>\n"
            + "  </div>\n"
            + "\n"
            + "    <div id=\"control\" class=\"control\">\n"
            + "      <div id=\"display\" class=\"\">\n"
            + "        <strong>Reproduciendo: </strong>\n"
            + "        <span id=\"display_tema\">Lorem ipsum dolor sit amet consectetur</span>\n"
            + "        <div id=\"reloj\">12:00:00</div>\n"
            + "        <span> Trasmitiendo: <i class=\"material-icons left\">speaker_phone</i></span>\n"
            + "      </div>\n"
            + "      <div id=\"gp_bt\" class=\"gp_bt_rep\">\n"
            + "        <div class=\"row\">\n"
            + "          <div class=\"col l4 m4 s12\">\n"
            + "            <button class=\"btn_rep btn\" id=\"btn_mp3\">\n"
            + "              <i class=\"material-icons left\">music_video</i>Audio\n"
            + "            </button>\n"
            + "          </div>\n"
            + "          <div class=\"col l4 m4 s12\">\n"
            + "            <button class=\"btn_rep btn\" id=\"btn_video\">\n"
            + "              <i class=\"material-icons left\">live_tv</i> Video\n"
            + "            </button>\n"
            + "          </div>\n"
            + "          <div class=\"col l4 m4 s12\">\n"
            + "            <button class=\"btn_rep btn\" id=\"btn_config\">\n"
            + "              <i class=\"material-icons left\">settings_applications</i> Config\n"
            + "            </button>\n"
            + "          </div>\n"
            + "\n"
            + "        </div>\n"
            + "\n"
            + "      </div>\n"
            + "    </div>\n"
            + "\n"
 
            + "    <div class=\"panel-menu row\">\n"
            + "      <div class=\"panel_busqueda col l8 m8 s12\">\n"
            + "        <div class=\"box_busqueda\">\n"
            + "          <input class=\"validate\" id=\"busq\" placeholder=\"Buscar Tema\"> \n"
            + "          <i class=\"material-icons left\" onClick='busq_youtube()'>search</i>\n"
            + "          <div class=\"box_resultBusq\"></div>\n"
            + "        </div>\n"
            + "        <div id='resulBusq'>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "      <div class=\"panel_listaRep col l4 m4 s12\">\n"
            + "        <button id='act_listRep' class='btn col l12 m12 s12'><Strong>Leer Lista:</Strong></button>\n"
            + "        <div id='lista_reproduccion'></div>\n"
            + "        <button id='envia_listRep' class='btn col l12 m12 s12' onCLick='enviaDatServidor()'><Strong>Envia Lista:</Strong></button>\n"
            + "      </div>\n"
            + "    </div>\n"
            
            + "    <div class=\"row\">\n"
            + "        <div class=\"ctrl_video col l4 m4 s12\">\n"
            + "          <div class=\"ctrl_videoNombre\">\n"
            + "            <span id=\"ctrl_txt\"> Panel Video</span>\n"
            + "            <i class=\"material-icons left\">burst_mode</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"activa_video\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "        <div class=\"ctrl_video col l4 m4 s12\">\n"
            + "          <div class=\"ctrl_videoNombre\">\n"
            + "            <span id=\"ctrl_txt\"> Full Screen</span>\n"
            + "            <i class=\"material-icons left\">aspect_ratio</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"fullScreen\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "        <div class=\"ctrl_video col l4 m4 s12\">\n"
            + "          <div class=\"ctrl_videoNombre\">\n"
            + "            <span id=\"ctrl_txt\"> Panel Lista</span>\n"
            + "            <i class=\"material-icons left\">chrome_reader_mode</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"lista_rep\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "        </div>\n"
            
            + "\n"
            + "\n"
            + "    <div class=\"subtitulo\">Control de Audio</div>\n"
            + "    <div class=\"row ctrl_boxAudio\">\n"
            + "      <div class=\"col l4 m6 s12\">\n"
            + "        <div class=\"ctrl_box\">\n"
            + "          <div class=\"ctrl_nombre\">\n"
            + "            <span>Amplificador</span>\n"
            + "            <i class=\"material-icons left\">power_settings_new</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"ctrl_ampl\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "\n"
            + "      <div class=\"col l4 m6 s12\">\n"
            + "        <div class=\"ctrl_box\">\n"
            + "          <div class=\"ctrl_nombre\">\n"
            + "            <span>PISO 1</span>\n"
            + "            <i class=\"material-icons left\">volume_up</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"ctrl_p1\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "\n"
            + "      <div class=\"col l4 m6 s12\">\n"
            + "        <div class=\"ctrl_box\">\n"
            + "          <div class=\"ctrl_nombre\">\n"
            + "            <span>PISO 2</span>\n"
            + "            <i class=\"material-icons left\">volume_off</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"ctrl_p2\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "\n"
            + "      <div class=\"col l4 m6 s12\">\n"
            + "        <div class=\"ctrl_box\">\n"
            + "          <div class=\"ctrl_nombre\">\n"
            + "            <span>PISO 3</span>\n"
            + "            <i class=\"material-icons left\">volume_off</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"ctrl_p3\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "\n"
            + "      <div class=\"col l4 m6 s12\">\n"
            + "        <div class=\"ctrl_box\">\n"
            + "          <div class=\"ctrl_nombre\">\n"
            + "            <span>PISO 4</span>\n"
            + "            <i class=\"material-icons left\">volume_off</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"ctrl_p4\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "\n"
            + "      <div class=\"col l4 m6 s12\">\n"
            + "        <div class=\"ctrl_box\">\n"
            + "          <div class=\"ctrl_nombre\">\n"
            + "            <span>OFICINA</span>\n"
            + "            <i class=\"material-icons left\">volume_off</i>\n"
            + "          </div>\n"
            + "          <div class=\"switch\">\n"
            + "            <label>\n"
            + "              Off\n"
            + "              <input type=\"checkbox\" id=\"ctrl_offc\">\n"
            + "              <span class=\"lever\"></span>\n"
            + "              On\n"
            + "            </label>\n"
            + "          </div>\n"
            + "        </div>\n"
            + "      </div>\n"
            + "\n"
            + "    </div>\n"
            + "    <div id=\"categoria\"></div>\n"
            + "    <div id=\"panel_configuracion\"></div>\n"
            + "\n"
            + "\n"
            + "  </div><!---fin container-->\n"
            + "  <footer>\n"
            + "\n"
            + "  </footer>\n"
            + "<div class=\"switch\">\n" 

            + "</body>\n"
            + "<script src=\"https://telcotronics.com/js/app_audio_control.js\"></script>\n"
            + "\n"
            + "\n"
            + "</html>";

    String css = "@font-face {\n"
            + "    font-family: Open24Display;\n"
            + "    src: url(\"../fonts/Open24Display.ttf\");\n"
            + "}\n"
            + "@font-face {\n"
            + "    font-family: Righteous;\n"
            + "    src: url(\"../fonts/Righteous-Regular.ttf\");\n"
            + "}\n"
            + "\n"
            + ".control {\n"
            + "    display: grid;\n"
            + "    grid-template-columns: auto auto;\n"
            + "    grid-template-rows: auto auto;\n"
            + "  }\n"
            + "\n"
            + "#display{\n"
            + "    background-color: rgba(0,200,100,.5);\n"
            + "    height: 118px;\n"
            + "    background-image: url(\"../img/spectro.gif\");\n"
            + "    background-repeat: no-repeat;\n"
            + "    background-position: right;\n"
            + "    background-size: 80%;\n"
            + "    font-family: Righteous,serif;\n"
            + "    margin: 2px;\n"
            + "\n"
            + "    grid-column-start: 1;\n"
            + "    grid-column-end: 3;\n"
            + "}\n"
            + "#gp_bt{\n"
            + "    grid-column-start: 1;\n"
            + "    grid-column-end: 3;\n"
            + "}\n"
            + "#display strong{\n"
            + "    font-size: 18px;\n"
            + "}\n"
            + "#reloj{\n"
            + "    font-size: 25px;\n"
            + "    font-family: Open24Display,serif;\n"
            + "    margin: 5px;\n"
            + "}\n"
            + "\n"
            + ".btn{\n"
            + "    \n"
            + "}\n"
            + ".btn_rep{\n"
            + "    width: 100%;\n"
            + "    margin-top: 5px;\n"
            + "}";

    public String initWeb() {
        return web;
    }

    public String css() {
        return css;
    }
}
