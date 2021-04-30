package com.example.infoproject

import android.content.Context
import android.graphics.RectF

class Laser (context : Context, ScreenX : Int, ScreenY : Int,  ligne : Int) : Bullet (context, ScreenX, ScreenY, ligne, "ship") {

    override var position =
        RectF(
            ScreenY/11f + 25f,
            ScreenY * ligne / 11f - hauteur / 2f,
            ScreenY/11f + 25f + largeur,
            ScreenY * ligne / 11f + hauteur / 2f
        )


    override fun update(fps : Long, typemob : Any, enemies: ArrayList<Enemy>,
                        missiles: ArrayList<Missile>, ship: Ship) {
        super.update(fps,typemob,enemies,missiles,ship) //récupère la fonction update telle qu'elle est définie dans Bullet
        if (missiles.isNotEmpty()){
            for (Missile in missiles) {
                if (position.intersect(Missile.position)) {
                    Missile.Kaboum()
                    visible = false
                    break
                }
            }
        }
        if (enemies.isNotEmpty()){
            for (Enemy in enemies) {
                if (position.intersect(Enemy.position)) {
                    Enemy.degat()
                    visible = false
                    break
                }
            }
        }

        if (position.right >= ScreenX) {
            visible = false

        }

    }
}