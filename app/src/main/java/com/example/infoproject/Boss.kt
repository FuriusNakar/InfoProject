package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import java.util.ArrayList

class Boss(context : Context, private val ScreenX : Int, private val ScreenY : Int, ligne : Int, typeboss : Int, vieMulti : Int) {


    var vie = 25 + 10 * vieMulti
    private val largeur = ScreenY / 6f
    private val hauteur = ScreenY / 6f


    //pos
    val position =
        RectF(
            ScreenX - largeur - 20f,
            ScreenY * ligne / 11f - hauteur/2 ,
            ScreenX - 20f,
            ScreenY * ligne /11f + hauteur/2
        )
    //speed
    private var speed = 25f

    //fction qui recois dans "type" par pewpewview


    lateinit var Bbitmap : Bitmap

    private var shootingList = ArrayList<Int>()


    init{

            when (typeboss){
                1 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alien_boss)
                2 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dragon_boss)
                3 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rooster)
            }

        Bbitmap = createScaledBitmap(Bbitmap, largeur.toInt(), hauteur.toInt(), false)


        shootingList.add((2..8).random())
        var intToAdd = (2..8).random()
        for (i in 0..2){
            while (intToAdd in shootingList){
                intToAdd = (2..8).random()
            }
            shootingList.add(intToAdd)
        }
    }

    private var signOffset = -1

    var isShooting = false

    var ligneWhereShot = 0

    private var intToAdd = 0

    fun update(fps : Long){
        isShooting = false
        if (vie > 0){
            intToAdd = 0
            for(ligneShoot in shootingList){
                if (position.top > ligneShoot * ScreenY / 11f - 5.5f * hauteur / 10f && position.bottom < ligneShoot * ScreenY / 11f + 5.5f * hauteur / 10f) {
                    ligneWhereShot = ligneShoot

                    intToAdd = (2..8).random()
                    while (intToAdd in shootingList){
                        intToAdd = (2..8).random()
                    }

                    isShooting = true
                }
            }

            shootingList.remove(ligneWhereShot)
            if(intToAdd != 0){
                shootingList.add(intToAdd)
            }

            if (position.top < 1f * ScreenY / 11f || position.bottom > 9f * ScreenY / 11f ){
                if(position.left <= 7f * ScreenX / 10f){
                    signOffset = 1
                }

                if(position.right >= 9f * ScreenX / 10f){
                    signOffset = -1
                }

                position.offset(signOffset * ScreenY / 20f, speed)
                speed *= - 1

                isShooting = false
            }
            position.top -= speed / fps
            position.bottom = position.top + hauteur
        }
    }
    fun degat(){
        vie -= 1
    }
}
