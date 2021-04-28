package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import kotlin.random.Random

open class Bullet(context: Context, val ScreenX: Int, val ScreenY: Int, val ligne: Int, val typemob: Any) {
    //booleen qui sert pour check si le bullet est vivant ou non --> trash collector
    var visible = true

    val largeur = ScreenY / 33f
    val hauteur = ScreenY / 33f
    val frequenceshoot = arrayListOf<>(1,2,3,4)

    //pos
    open var position =
        RectF(
            ScreenX - largeur - 0f,
            ScreenY * ligne / 11f - hauteur / 2,
            ScreenX - 0f,
            ScreenY * ligne / 11f + hauteur / 2
        )

    //SPEEEEEED
    private val SPEEEEED = 100f   //rapido bb

    // donnée accessible hors class --> companion (fait le travail de NomClass.NomMethode)
    companion object {

        lateinit var Bubitmap: Bitmap
    }

    init {
        when (typemob) {
            1 -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.slime)
            2 -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fire)
            3 -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.egg)
            "ship" -> Bubitmap = BitmapFactory.decodeResource(context.resources, R.drawable.laser)
        }
        Bubitmap = Bitmap.createScaledBitmap(Bubitmap, largeur.toInt(), hauteur.toInt(), false)
    }

    open fun update(fps: Long, typemob: Any, enemies: ArrayList<Enemy>,
        missiles: ArrayList<Missile>, ship: Ship
    ) {
        if (typemob == "ship") {
            if (position.right < ScreenX) {
                position.right += 2 * SPEEEEED / fps
            }
            position.left = position.right - largeur
        } else {
            if (position.left > 0) {
                position.left -= SPEEEEED / fps
            }
            position.right = position.left + largeur
        }

    }
    fun shoot(string : String) {
        //fction qui place les bullet avec un offset
        val random = Random.nextInt()
        when (string) {
            "ship" -> position.offset(5f,0f)

            else -> {
            for (i in frequenceshoot[random]){
                position.offset(-5f,0f)

            }
        }

        }
    }

    }