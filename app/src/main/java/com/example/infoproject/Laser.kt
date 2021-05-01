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
                        missiles: ArrayList<Missile>, ship: Ship, bosses: ArrayList<Boss>) {
        super.update(fps,typemob,enemies,missiles,ship, bosses) //récupère la fonction update telle qu'elle est définie dans Bullet
        if (missiles.isNotEmpty()){
            for (Missile in missiles) {
                if (RectF(position.left-ScreenX/4.5f,position.top,position.right,position.bottom).intersect(Missile.position)) {
                    Missile.Kaboum()
                    visible = false
                    break
                }
            }
        }

        if (enemies.isNotEmpty() && visible){
            for (Enemy in enemies) {
                if (Enemy.visible && RectF(position.left-ScreenX/4.5f,position.top,position.right,position.bottom).intersect(Enemy.position)) {
                    Enemy.degat()
                    visible = false
                    break
                }
            }
        }

        if (bosses.isNotEmpty() && visible){
            if (RectF(position.left-ScreenX/4.5f,position.top,position.right,position.bottom).intersect(bosses[0].position)) {
                bosses[0].degat()
                visible = false
            }
        }

        if (position.right >= ScreenX) {
            visible = false

        }
    }
}
