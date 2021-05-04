package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Enemy (context : Context, ScreenX : Int, ScreenY : Int, typemob : Int, ligne : Int) {
    //Paramètre de Enemy : Context (instaure les commandes de base), Dimension de l'écran (taille du ship varie d'un appareil à uin autre )
    // ligne sert pour le spawn (random)
    // transfore image du enemy en Bitmap (en pixel) -> pratique pour hitbox d'après ce que j'ai compris

    //dans le View il y a moulte enemy -> posy random, posx initial fixé
    private var vie = 1
    //redimensionnement
    private val largeur = ScreenY / 11f
    private val hauteur = ScreenY / 11f

    //booleen qui sert pour check si enemy vivant ou non --> trash collector
    var visible = true

    val lignePos = ligne

    val type = typemob

    //pos
    val position =
        RectF(
            ScreenX - 0f,
            ScreenY * ligne / 11f - hauteur/2,
            ScreenX - largeur - 0f,
            ScreenY * ligne / 11f + hauteur/2
        )

    //SPEEEEEED
    private val SPEEEEED = 100f   //rapido bb

    // donnée accessible hors class --> companion (fait le travail de NomClass.NomMethode)

    lateinit var Ebitmap : Bitmap


    //transformation et redimenssionnement du vaisseau en fction des dimenssion souhaité
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
        if (vie > 0) {
            if (position.right > 0) {
                position.left -= SPEEEEED / fps
                position.right = position.left + largeur
            } else if(position.right <= 0){
                points = -points
                vie = 0
                visible = false
            }


        }
        else {
            visible = false
        }
    }
    fun degat(){
        vie--
        visible = false
    }
}