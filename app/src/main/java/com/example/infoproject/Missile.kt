package com.example.infoproject

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle

class Missile (context : Context, ScreenX : Float, ScreenY : Float,  ligne : Int, typemob : Any, pos_gauche : Float) : Bullet (context, ScreenX, ScreenY, ligne, typemob) {

    override var position =
        RectF(
            pos_gauche - 5f - largeur,
            ScreenY * ligne / 11f - hauteur / 2f,
            pos_gauche - 5f,
            ScreenY * ligne / 11f + hauteur / 2f
        )

    override fun update(fps : Long, typemob : Any, enemies: ArrayList<Enemy>,
                        missiles: ArrayList<Missile>, ship: Ship) {
        super.update(fps,typemob,enemies,missiles,ship) //récupère la fonction update telle qu'elle est définie dans Bullet

        if (position.intersect(ship.position)) {
            ship.degat()
            visible = false
        }
        else if (position.right <= 0){
            visible = false
        }
    }
    fun Kaboum (){
        visible = false
    }
}

