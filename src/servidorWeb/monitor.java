/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorWeb;

import Objetos.Info_reproduccion;
import javazoom.jl.player.Player;
import reproductor_de_musica.Zplayer;
import reproductor_de_musica.ventana_principal;

/**
 *
 * @author pablinux
 */
public class monitor {
    private Zplayer audioPlayer;
    //audioPlayer = new Zplayer(this);
    Player player;
    ventana_principal mp3;
    Info_reproduccion info = new Info_reproduccion();
   
    
}
