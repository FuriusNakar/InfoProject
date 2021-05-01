package com.example.infoproject

import android.graphics.RectF
import android.graphics.Bitmap
import android.content.Context
import android.graphics.BitmapFactory
import com.example.infoproject.PewPewActivity.Companion.Vso
import com.example.infoproject.PewPewView.Companion.shipligne


class Ship (context : Context, private val ScreenX : Int, private val ScreenY : Int) {
    //Paramètre du ship : Context (instaure les commandes de base), Dimension de l'écran (taille du ship varie d'un appareil à uin autre )

    // transfore image du ship en Bitmap (en pixel) -> pratique pour hitbox d'après ce que j'ai compris

    var Vbitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, Vso) //herédité de Bitmap

    var vie = 5
    var shield = 1f

    //redimensionnement
    private val largeur = ScreenY / 11f
    val hauteur = ScreenY / 11f

    //pos
    var position =
        RectF(20f, 6f * ScreenY / 11f - hauteur / 2f, 20f + largeur, 6* ScreenY / 11f + hauteur / 2f)

    //redimenssionnement du vaisseau sur le convas qu'on a
    init{Vbitmap = Bitmap.createScaledBitmap(Vbitmap, largeur.toInt(), hauteur.toInt(), false)}


    //function qui s'occupe du mouvement
    //ici la fonction est adaptée à notre cas (mouvement vertical et non horizontal)
    fun bouge (direction : Int) {
        // Can move as long as it doesn't try and leave the screen
        var bouge = direction
        if (vie > 0) {
            if (bouge == 1 && position.top > 2f / 11f * ScreenY) {
                position.top -= hauteur
                shipligne--
            } else if (bouge == -1 && position.bottom < 8f / 11f * ScreenY) {
                position.top += hauteur
                shipligne++
            }
            position.bottom = position.top + hauteur
        }
    }

    fun degat(){
        if (shield == 1f) {
            shield--
        } else {
            vie--
        }
    }

    fun shieldRegeneration () {
        if (shield != 1f){
            shield += 0.1f
        }
    }



}
