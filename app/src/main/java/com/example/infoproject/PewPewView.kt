package com.example.infoproject

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.view.SurfaceView
import android.util.Log
import android.view.MotionEvent
import android.view.View

class PewPewView(context: Context, private val size: Point) : SurfaceView(context),
    Runnable {

    private val jeuxThread = Thread(this)
    private val playing = false
    private val pause = true
    private val canvas = Canvas()
    private var ship = Ship(context, size.x, size.y)
    private val typemob = arrayListOf<Int>(1,2,3)
    //private val ligne = arrayListOf<>(1,2,3,4,5,6)
    private val ligne : Int
    //liste d'enemy + nombre qui spawn pour ensuite (contrainte pour Boss)
    private var enemies = ArrayList<Enemy>()
    private val nbre_enemies = 0

    //val des bullet
    //private val missile = Bullet(context,size.x,size.y, ligne, typemob)
    //private val laser = Bullet(context,size.x,size.y,ligne, "ship")
    private var missile = arrayListOf<>(Bullet)
    private var laser = arrayListOf<>(Bullet)

    private var score = 0
    private var vie = 5


    fun spawn(){


    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.up_arrow -> ligne ++

                R.id.down_arrow -> ligne --

                R.id.fire_button -> shipshoot
            }
        }

    }
}







}