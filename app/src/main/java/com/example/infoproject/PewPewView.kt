package com.example.infoproject

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.media.MediaPlayer
import android.os.Bundle
import android.view.SurfaceView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import kotlinx.android.synthetic.main.activity_main.*

class PewPewView(context: Context, private val size: Point) : View(context),
    Runnable {

    private val jeuxThread = Thread(this)
    private val playing = false
    private val pause = true
    private val canvas = Canvas()
    private var ship = Ship(context, size.x, size.y)
    private val typemob = arrayListOf<Int>(1, 2, 3)


    companion object{
        var ligne = 6
    }


    //liste d'enemy + nombre qui spawn pour ensuite (contrainte pour Boss)
    private var enemies = ArrayList<Enemy>()
    private var nbre_enemies = 0
    private var lasers = ArrayList<Laser>()

    //val des bullet
    //private val missile = Bullet(context,size.x,size.y, ligne, typemob)
    //private val laser = Bullet(context,size.x,size.y,ligne, "ship")
    //private var missile = arrayListOf<>(Bullet)
    //private var laser = arrayListOf<>(Bullet)

    private var score = 0
    private var vie = 5

    fun spawn(f: Int) {
        val ligne_spawn = List(8) { Random.nextInt(2, 9) }
        val nbr_ennemis_spawnné = ligne_spawn[0] - 1

        //fction de spawn d'enemy en fction du type
        when (typemob[f]) {
            1 -> for (i in 0..nbr_ennemis_spawnné) {
                enemies.add(Enemy(context, size.x, size.y, 1, ligne_spawn[i]))
                nbre_enemies++
            }
            2 -> for (i in 0..nbr_ennemis_spawnné) {
                enemies.add(Enemy(context, size.x, size.y, 2, ligne_spawn[i]))
                nbre_enemies++
            }
            3 -> for (i in 0..nbr_ennemis_spawnné) {
                enemies.add(Enemy(context, size.x, size.y, 3, ligne_spawn[i]))
                nbre_enemies++


            }
        }
    }

    private fun draw(){
        //fction responsable du dessiner les bitmaps sur imageview


    }

    override fun run() {}


}








