package com.example.infoproject

import android.content.Context

class Laser (context : Context, ScreenX : Float, ScreenY : Float,  ligne : Int) : Bullet (context, ScreenX, ScreenY, ligne, "ship") {
    override fun update(fps : Long, typemob : Any, enemies: ArrayList<Enemy>,
                        missiles: ArrayList<Missile>, ship: Ship) {
        super.update(fps,typemob,enemies,missiles,ship) //récupère la fonction update telle qu'elle est définie dans Bullet
        for (Missile in missiles) {
            if (position.intersect(Missile.position)) {
                Missile.Kaboum()
                visible = false
                break
            }
        }
        for (Enemy in enemies) {
            if (position.intersect(Enemy.position)) {
                Enemy.degat()
                visible = false
                break
            }
        }
        if (position.left >= ScreenX) {
            visible = false
        }

    }
}