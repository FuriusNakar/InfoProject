package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import kotlin.random.Random

class Enemy (context : Context, private val ScreenX : Int, private val ScreenY : Int) {
    //Paramètre de Enemy : Context (instaure les commandes de base), Dimension de l'écran (taille du ship varie d'un appareil à uin autre )
    // ligne sert pour le spawn (random)
    // transfore image du enemy en Bitmap (en pixel) -> pratique pour hitbox d'après ce que j'ai compris


    //redimensionnement
    private val largeur = ScreenY / 11f
    val hauteur = ScreenY / 11f

    //booleen qui sert pour check si enemy vivant ou non --> trash collector
    var visible = true


    //pos
    val position =
        RectF(
            ScreenX - largeur,
            ScreenY /2f - hauteur / 2f,
            ScreenX.toFloat(),
            ScreenY / 2f + hauteur / 2f
        )

    //SPEEEEEED
    private val SPEEEEED = -50f   //rapido bb

    // donnée accessible hors class --> companion (fait le travail de NomClass.NomMethode)
    companion object{
        var Ebitmap1 : Bitmap? = null
        var Ebitmap2 : Bitmap? = null

        var nbre_enemy = 0
}

    //transformation et redimenssionnement du vaisseau sur le convas qu'on a
init{
        Ebitmap1 =
            BitmapFactory.decodeResource(context.resources, R.drawable.playership) //mettre image enemy
        Ebitmap2 =
            BitmapFactory.decodeResource(context.resources, R.drawable.playership)

        Ebitmap1 = Bitmap.createScaledBitmap(Ebitmap1, largeur.toInt(), hauteur.toInt(), false)
        Ebitmap2= Bitmap.createScaledBitmap(Ebitmap2, largeur.toInt(), hauteur.toInt(), false)
}

open fun L() {
    for (i in 1..11) {
        val ligne = arrayListOf<Int>(ScreenX * i / 11)
    }
    //pouet pouet cacahuetes
}

    fun uptade(fps : Long){
        if (position.left > 0){
            position.left -= SPEEEEED/fps
        }
        position.right = position.left + largeur
    }

    //fction qui fait spawn les enemy sur une ligne random (entre ligne 2-9 compris)
    fun spawn(){


        val random_ligne = Random.nextInt(2,10)
        position.top = L.ligne[random_ligne] - hauteur / 2f
        position.bottom = L.ligne[random_ligne] + hauteur / 2f

        nbre_enemy ++



    }
    //fction qui enregistre le hit du laser du vaisseau
    fun hit(){
        if (laser.position.left )
    }


}