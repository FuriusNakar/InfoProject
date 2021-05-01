package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class Boss(context : Context, private val ScreenX : Int, private val ScreenY : Int, ligne : Int, typeboss : Int, vieMulti : Int) {


    var vie = 25 * vieMulti
    val largeur = ScreenY / 6f
    val hauteur = ScreenY / 6f


    //pos
    val position =
        RectF(
            ScreenX - largeur - 20f,
            ScreenY * ligne / 11f - hauteur/2 ,
            ScreenX - 20f,
            ScreenY * ligne /11f + hauteur/2
        )
    //speed
    var speed = 25f

    //fction qui recois dans "type" par pewpewview


    lateinit var Bbitmap : Bitmap


    init{

            when (typeboss){
                1 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alien_boss)
                2 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dragon_boss)
                3 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rooster)
            }


        Bbitmap = createScaledBitmap(Bbitmap, largeur.toInt(), hauteur.toInt(), false)
    }

    var signOffset = -1

    fun update(fps : Long){
            if (vie > 0){
                if (position.top < 1.5f * ScreenY / 11f || position.bottom > 9f * ScreenY / 11f ){
                    if(position.left <= 7f * ScreenX / 10f){
                        signOffset = 1
                    }

                    if(position.right >= 9f * ScreenX / 10f){
                        signOffset = -1
                    }

                    position.offset(signOffset * ScreenY / 20f, speed)
                    speed *= - 1
                }
                position.top -= speed / fps
                position.bottom = position.top + hauteur
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