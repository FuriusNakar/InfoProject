package com.example.infoproject

import android.graphics.RectF
import android.graphics.Bitmap
import android.content.Context
import android.graphics.BitmapFactory
import com.example.infoproject.PewPewActivity.Companion.Vso
import com.example.infoproject.PewPewView.Companion.shipligne


class Ship (context : Context, private val ScreenX : Int, private val ScreenY : Int) {
    //Création de ma Bitmap du vaisseau
    var Vbitmap: Bitmap = BitmapFactory.decodeResource(context.resources, Vso)

    //Variable de vie et de bouclier du vaisseau
    var vie = 5
    var shield = 0f

    //Calcul des dimensions du vaisseau
    private val largeur = ScreenY / 11f
    private val hauteur = ScreenY / 11f

    //Création de la boite de collision du vaisseau
    var position =
        RectF(20f, 6f * ScreenY / 11f - hauteur / 2f, 20f + largeur, 6* ScreenY / 11f + hauteur / 2f)

    //redimenssionnement de l'image du vaisseau en fonction des dimensions de la boite de collision
    init{Vbitmap = Bitmap.createScaledBitmap(Vbitmap, largeur.toInt(), hauteur.toInt(), false)}


    //function qui s'occupe du mouvement du vaisseau
    fun bouge (direction : Int) {
        if (vie > 0) {
            //le vaisseau peut bouger tant qu'il se trouve dans la zone de jeu
            if (direction == 1 && position.top > 2f / 11f * ScreenY) {
                position.top -= hauteur
                shipligne--
            } else if (direction == -1 && position.bottom < 8f / 11f * ScreenY) {
                position.top += hauteur
                shipligne++
            }
            position.bottom = position.top + hauteur
        }
    }

    //Variable chronometrant le temps écoulé
    private var timeShieldWentDown = System.currentTimeMillis()

    fun degat(){
        //Fonction infligeant des dégats au ship ou au bouclier si il est chargé. Elle update le timer de rechargement.
        if (shield >= 1f) {
            shield = 0f
        } else {
            vie--
        }
        timeShieldWentDown = System.currentTimeMillis()
    }

    fun shieldRegeneration () {
        //Si il s'est écoulé au moins 10secondes entre la dernière activation de la fonction dégat et l'activation de la fonction shieldRegenerationa lors le shield reprendra une valeur de 1 (sera rechargé)
        if (shield < 1f && System.currentTimeMillis() - timeShieldWentDown >= 10000){
            shield = 1f
        }
    }
}
