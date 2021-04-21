package com.example.infoproject

import android.graphics.RectF
import android.graphics.Bitmap
import android.content.Context
import android.graphics.BitmapFactory



class Ship (context : Context, private val ScreenX : Float, private val ScreenY : Float) {
    //Paramètre du ship : Context (instaure les commandes de base), Dimension de l'écran (taille du ship varie d'un appareil à uin autre )

    // transfore image du ship en Bitmap (en pixel) -> pratique pour hitbox d'après ce que j'ai compris

    var Vbitmap : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.playership) //herédité de Bitmap
         //mettre emplacement du ship choisi
        // faire programme qui choisit le ship au début du lancement et puis link l'emplacement du fichier à ici
        // style : startscreen.typeship("sortie/id du ship choisi")

    var vie = 5

    //redimensionnement
    private val largeur = ScreenY / 11f
    val hauteur = ScreenY / 11f

    //pos
    val position = RectF(20f, ScreenY / 2f - hauteur / 2f, 20f + largeur, ScreenY/2f + hauteur / 2f)

    //SPEEEEEED
    //private val SPEEEEED = 500000000f   //rapido bb

    // donnée accessible hors class --> companion (fait le travail de NomClass.NomMethode)
    companion object {
        // Which ways can the ship move
        const val stop = 0
        const val haut = 1
        const val bas = 2
    }
    //vaisseau bouge pas
    var bouge = stop

    //redimenssionnement du vaisseau sur le convas qu'on a
    init{Vbitmap = Bitmap.createScaledBitmap(Vbitmap, largeur.toInt(), hauteur.toInt(), false)}


    //function qui s'occupe du mouvement
    //ici la fonction est adaptée à notre cas (mouvement vertical et non horizontal)
    fun update(fps: Long) {     //type long = 64bit d'info donc précis
        // Move as long as it doesn't try and leave the screen
        while (vie > 0) {
            if (bouge == haut && position.top > 2f / 11f * ScreenY - hauteur / 2f) {
                position.top -= hauteur
            } else if (bouge == bas && position.bottom < 9f / 11f * ScreenY + hauteur / 2f) {
                position.top += hauteur
            }
            bouge = stop
            position.bottom = position.top + hauteur
        }
    }

    fun degat(){
        vie -= 1
    }


}
