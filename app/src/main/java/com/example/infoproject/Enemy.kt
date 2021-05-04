package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Enemy (context : Context, ScreenX : Int, ScreenY : Int, typemob : Int, ligne : Int) {
    //redimensionnement
    private val largeur = ScreenY / 11f
    private val hauteur = ScreenY / 11f

    //booleen qui sert pour check si enemy vivant ou non --> trash collector
    var visible = true

    val lignePos = ligne

    val type = typemob

    //Défini la position, boite de collisions de l'ennemi
    val position =
        RectF(
            ScreenX - 0f,
            ScreenY * ligne / 11f - hauteur/2,
            ScreenX - largeur - 0f,
            ScreenY * ligne / 11f + hauteur/2
        )

    //Vitesse
    private val SPEEEEED = 100f

    lateinit var Ebitmap : Bitmap

    //redimenssionnement de l'ennemi en fonction des dimensions et images souhaitées
    var points = 0
    init {
        when (typemob){
            1 -> {
                Ebitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alien)
                points = 5
            }
            2 -> {
                Ebitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dragon)
                points = 5
            }
            3 -> {
                Ebitmap = BitmapFactory.decodeResource(context.resources, R.drawable.chicken)
                points = 5
            }
        }

        Ebitmap = Bitmap.createScaledBitmap(Ebitmap, largeur.toInt(), hauteur.toInt(), false)
    }

    fun update(fps : Long) {
        //fonction qui fait les ennemi se déplacer et qui les tuent si ils sortent de l'écran
        if (visible) {
            if (position.right > 0) {
                position.left -= SPEEEEED / fps
                position.right = position.left + largeur
            } else if (position.right <= 0) {
                points = -points
                visible = false
            }
        }
    }

    fun degat(){
        //fonction appelée par le laser pour infliger un dégat à l'ennemi qu'il touche
        visible = false
    }
}