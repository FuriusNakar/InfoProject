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
import android.view.SurfaceHolder;

class PewPewView(context: Context, private val size: Point) : SurfaceView(context),
    Runnable {


    private val jeuxThread = Thread(this)
    private var playing = false
    private var pause = true
    private var ship = Ship(context, size.x, size.y)
    private val typemob = 1
    private var canvas: Canvas = Canvas()

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

    fun spawn() {
        val ligne_spawn = List(7) { Random.nextInt(3, 9) }
        val nbr_ennemis_spawnné = ligne_spawn[0] - 1

        //fction de spawn d'enemy en fction du type
        when (typemob) {
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

    var BackgroundNumber = 0
    val Backgroundlist = intArrayOf(R.drawable.barren_planet,R.drawable.lava_planet,R.drawable.temperate_planet)

    private fun draw(){
        //fction responsable du dessiner les bitmaps sur imageview
        if (holder.surface.isValid) {
            var Screenbitmap: Bitmap = BitmapFactory.decodeResource(context.resources,Backgroundlist[BackgroundNumber])

            Screenbitmap = Bitmap.createScaledBitmap(Screenbitmap, size.x, size.y, false)

            var upBitmap: Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.moving_up_arrow)
            upBitmap = Bitmap.createScaledBitmap(upBitmap, 2*size.y/11, 2*size.y/11, false)

            var downBitmap: Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.moving_down_arrow)
            downBitmap = Bitmap.createScaledBitmap(downBitmap, 2*size.y/11, 2*size.y/11, false)

            var pewBitmap: Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.fire_button)
            pewBitmap = Bitmap.createScaledBitmap(pewBitmap, 2*size.y/11, 2*size.y/11, false)

            var hpBitmap: Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.pentagone_vert)
            hpBitmap = Bitmap.createScaledBitmap(hpBitmap, size.y/11, size.y/11, false)

            var shieldBitmap: Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.pentagone_bleu)
            shieldBitmap = Bitmap.createScaledBitmap(shieldBitmap, size.y/11, size.y/11, false)

            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawBitmap(Screenbitmap,0f,0f,null)

            if (lasers.isNotEmpty()){
                for (laser in lasers){
                    canvas.drawBitmap(laser.Bubitmap,laser.position.left,laser.position.top,null)
                }
            }

            if (enemies.isNotEmpty()){
                for (enemi in enemies){
                    canvas.drawBitmap(enemi.Ebitmap,enemi.position.left,enemi.position.top,null)
                }
            }

            if (ship.vie > 0){
                var finali = 0
                for (i in 0 until ship.vie){
                    canvas.drawBitmap(hpBitmap,5f + i*size.y.toFloat()/11,5f,null)
                    finali++
                }
                if (ship.shield == 1) {
                    canvas.drawBitmap(shieldBitmap,5f + finali*size.y.toFloat()/11,5f,null)
                }
            }
            // Now draw the player spaceship
            canvas.drawBitmap(ship.Vbitmap, ship.position.left,
                ship.position.top,null)

            canvas.drawBitmap(upBitmap,20f,size.y.toFloat() - (2 * size.y / 11),null)
            canvas.drawBitmap(downBitmap,2*size.y.toFloat() /11 + 20f,size.y.toFloat() - (2 * size.y / 11),null)
            canvas.drawBitmap(pewBitmap,size.x.toFloat() - 2*size.y/11 - 20f,size.y.toFloat() - (2 * size.y / 11),null)

            holder.unlockCanvasAndPost(canvas)

        }
    }

    fun pause() {
        playing = false
        try {
            jeuxThread.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }
    }

    // If SpaceInvadersActivity is started then
    // start our thread.
    fun resume() {
        playing = true
        jeuxThread.start()
    }


    override fun run() {
        while (playing){
            draw()
            if (enemies.isEmpty()){
                spawn()
            }
        }
    }

    var pewSound : MediaPlayer? = null
    fun pewSound () {
        if (pewSound == null) {
            pewSound = MediaPlayer.create(context, R.raw.pew)
            pewSound!!.isLooping = false
            pewSound!!.start()
        } else pewSound!!.start()
    }

    var clic = true

    companion object{
        var shipligne = 6
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val motionArea = size.y - (2 * size.y / 11)
        when (motionEvent.action and MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            // Or moved their finger while touching screen
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE-> {
                if (motionEvent.y > motionArea && clic) {
                    if (motionEvent.x > 20f && motionEvent.x < 2*size.y/11 + 20f) {
                        ship.bouge(1)
                        clic = false
                    } else if (motionEvent.x > 2*size.y/11 + 20f && motionEvent.x < 4*size.y/11 + 20f){
                        ship.bouge(-1)
                        clic = false
                    } else if (motionEvent.x > size.x - 2*size.y/11 - 20f && motionEvent.x < size.x - 20f) {
                        pewSound()
                        clic = false
                        lasers.add(Laser(context,size.x,size.y, shipligne))
                    }
                }
            }

            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_UP -> clic = true
        }
        return true
    }



}








