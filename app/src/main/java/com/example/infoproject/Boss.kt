package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Boss(context : Context, private val ScreenX : Float, private val ScreenY : Float, ligne : Int, typeboss : Int,  var view : PewPewView) {


    var vie = 20
    val largeur = ScreenY / 11f
    val hauteur = ScreenY / 11f


    //pos
    val position =
        RectF(
            ScreenX - largeur - 20f,
            ScreenY * ligne / 11f - hauteur/2 ,
            ScreenX - 20f,
            ScreenY * ligne /11f + hauteur/2
        )
    //speed
    var speed = 60f

    //fction qui recois dans "type" par pewpewview

    companion object {
        lateinit var Bbitmap : Bitmap
        }

    init{

            when (typeboss){
                1 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alien_boss)
                2 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dragon_boss)
                3 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rooster)
            }


        Bbitmap = Bitmap.createScaledBitmap(Bbitmap, largeur.toInt(), hauteur.toInt(), false)
    }

    fun update(fps : Long){
            while (vie > 0){
                position.top -= speed / fps
                if (position.top < 2f * ScreenY / 11f || position.bottom > 9f * ScreenY / 11f ){//screenhight à faire sur pewpewview
                    speed *= - 1
                    position.offset(0f, speed)
                }
        }
    }
    fun degat(){
        vie -= 1
    }




    /*
    fun update(interval: Double) {
        var up = (interval * obstacleVitesse).toFloat()
        obstacle.offset(0f, up)
        if (obstacle.top < 0 || obstacle.bottom > view.screenHeight) {
            obstacleVitesse *= -1
            up = (interval * 3 * obstacleVitesse).toFloat()
            obstacle.offset(0f, up)
        }
    }
    fun rebondit() {
        speed = -speed
        position.offset(0F, 3.0F*speed)
    }
*/

}