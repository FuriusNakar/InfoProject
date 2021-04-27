package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import kotlin.random.Random

class Enemy (context : Context, val ScreenX : Int, val ScreenY : Int, val typemob : Int, val ligne : Int) {
    //Paramètre de Enemy : Context (instaure les commandes de base), Dimension de l'écran (taille du ship varie d'un appareil à uin autre )
    // ligne sert pour le spawn (random)
    // transfore image du enemy en Bitmap (en pixel) -> pratique pour hitbox d'après ce que j'ai compris

    //dans le View il y a moulte enemy -> posy random, posx initial fixé
    var vie = 1
    //redimensionnement
    private val largeur = ScreenY / 11f
    val hauteur = ScreenY / 11f

    //booleen qui sert pour check si enemy vivant ou non --> trash collector
    var visible = true



    //pos
    val position =
        RectF(
            ScreenX - largeur - 0f,
            ScreenY * ligne / 11f - hauteur/2,
            ScreenX - 0f,
            ScreenY * ligne / 11f + hauteur/2
        )

    //SPEEEEEED
    private val SPEEEEED = 50f   //rapido bb

    // donnée accessible hors class --> companion (fait le travail de NomClass.NomMethode)
    companion object{

        lateinit var Ebitmap : Bitmap

        //var nbre_enemy = 0
}

    //transformation et redimenssionnement du vaisseau en fction des dimenssion souhaité
    init {

            when (typemob){
                1 -> Ebitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alien)
                2 -> Ebitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dragon)
                3 -> Ebitmap = BitmapFactory.decodeResource(context.resources, R.drawable.chicken)
            }

        Ebitmap = Bitmap.createScaledBitmap(Ebitmap, largeur.toInt(), hauteur.toInt(), false)
    }

    fun update(fps : Long) {
        while (vie > 0) {
            if (position.left > 0) {
                position.left -= SPEEEEED / fps
            }
            position.right = position.left + largeur
        }
    }
    fun degat(){
        vie -= 1
    }

    fun shoot(){
        val frequenceshoot : Random()
        for (i in frequenceshoot.nextInt(1,3)){
            enemy.shoot

        }


    }
/*          a laisser
fun L(a : Int) : {
    var ligne = 0
    for (i in 1..11) {
        ligne = listOf<Int>(posx * i / 11)
    }
    return ligne
    //pouet pouet cacahuetes
}

    //fction qui fait spawn les enemy sur une ligne random (entre ligne 2-9 compris)
    fun spawn(){
        var spawnok = true
        val random_ligne = Random.nextInt(2,10)
        position.top = L.ligne[random_ligne] - hauteur / 2f
        position.bottom = L.ligne[random_ligne] + hauteur / 2f

        nbre_enemy ++

        when (nbre_enemy > 15){
             spawnok = false
        }
*/

    //fction qui enregistre le hit du laser du vaisseau



}