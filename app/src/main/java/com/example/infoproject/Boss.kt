package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Boss(context : Context, private val posx : Float, private val posy : Float, typeboss : Int, ligne : Int) {

    val largeur = posx / 10f
    val hauteur = posy / 4f

    var Bbitmap : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.playership)

    //pos
    val position =
        RectF(
            posx - largeur,
            posy * ligne - hauteur/2 ,
            posx,
            posy * ligne + hauteur/2
        )
    //speed
    val speed = 60f

    init{var Bbitmap = Bitmap.createScaledBitmap(Bbitmap, largeur.toInt(), hauteur.toInt(), false)}

    fun update(fps : Long){
        var en_vie = true
            while (en_vie){
                position.top -= speed / fps
                when (position.intersect(posx - largeur, 0f, posx, posy * + hauteur/2)){
                    rebondit()
                }
                if (position.top < 0) {
                //pas fini

            }
        }

    }
    fun rebondit() {
        speed = -speed
        position.offset(0F, 3.0F*speed)
    }




}