package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import kotlin.random.Random

open class Bullet(context: Context, val ScreenX: Int,ScreenY: Int,ligne: Int,typemob: Any) {
    //Classe définissant les projectiles

    //booleen qui sert pour check si le bullet est vivant ou non --> utile pour le trash collector
    var visible = true

    //Défini les dimensions du projectiles
    val largeur = ScreenY / 33f
    val hauteur = ScreenY / 33f

    //Défini la position (boite de collisions du projectile)
    open var position =
        RectF(
            ScreenX - largeur - 0f,
            ScreenY * ligne / 11f - hauteur / 2,
            ScreenX - 0f,
            ScreenY * ligne / 11f + hauteur / 2
        )

    //Défini la vitesse du projectile
    private val SPEEEEED = 200f   //rapido bb

    //Initialise la bitmap du projectile
    lateinit var Bubitmap: Bitmap

    init {
        //Choisi l'image à appliquer au projectile en fonction du type
        when (typemob) {
            1 -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.slime)
            2 -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fire)
            3 -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.egg)
            "ship" -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.laser)
        }
        //Redimentionne l'image dans la hitbox
        Bubitmap = Bitmap.createScaledBitmap(Bubitmap, largeur.toInt(), hauteur.toInt(), false)
    }

    open fun update(fps: Long, typemob: Any, enemies: ArrayList<Enemy>,
        missiles: ArrayList<Missile>, ship: Ship, bosses: ArrayList<Boss>
    ) {
        //Méthode de mouvement en fonction du type
        if (typemob == "ship") {
            if (position.right < ScreenX) {
                position.right += 2.5f * SPEEEEED / fps
            }
            position.left = position.right - largeur
        } else {
            if (position.left > 0) {
                position.left -= SPEEEEED / fps
            }
            position.right = position.left + largeur
        }

    }
}