package com.example.infoproject

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.media.MediaPlayer
import android.os.Bundle
import android.view.SurfaceView
import android.util.Log
import android.util.LogPrinter
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.view.SurfaceHolder;
import com.example.infoproject.PewPewActivity.Companion.bossMusic
import com.example.infoproject.PewPewActivity.Companion.campainMusic
import kotlin.random.nextInt

class PewPewView(context: Context, private val size: Point) : SurfaceView(context),
    Runnable {

    companion object{
        var shipligne = 6
        var score = 0
    }

    var jeuxThread = Thread(this)
    var playing = false
    private var paused = false
    private var ship = Ship(context, size.x, size.y)
    private var typemob = 1
    private var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()

    //liste d'enemy + nombre qui spawn pour ensuite (contrainte pour Boss)
    private var enemies = ArrayList<Enemy>()
    private var nbre_enemies = 0
    private var lasers = ArrayList<Laser>()
    private var missiles = ArrayList<Missile>()

    //val des bullet
    //private val missile = Bullet(context,size.x,size.y, ligne, typemob)
    //private val laser = Bullet(context,size.x,size.y,ligne, "ship")
    //private var missile = arrayListOf<>(Bullet)
    //private var laser = arrayListOf<>(Bullet)

    fun spawn() {
        val ligne_spawn = arrayListOf<Int>()
        var inttoadd = (2..8).random()
        ligne_spawn.add(inttoadd)
        while(ligne_spawn.size<7){
            var inttoadd = (2..8).random()
            var adding_int = true
            for (int in ligne_spawn){
                if (inttoadd == int){
                    adding_int = false
                }
            }
            if (adding_int){
                ligne_spawn.add(inttoadd)
            }
        }
        var nbr_ennemis_spawn = ligne_spawn[0] - 1
        if (nbr_ennemis_spawn < 4){
            nbr_ennemis_spawn++
        } else if (nbr_ennemis_spawn > 4){
            nbr_ennemis_spawn--
        }

        //fction de spawn d'enemy en fction du type
        when (typemob) {
            1 -> for (i in 0 until nbr_ennemis_spawn) {
                enemies.add(Enemy(context, size.x, size.y, 1, ligne_spawn[i]))
                nbre_enemies++
            }
            2 -> for (i in 0 until nbr_ennemis_spawn) {
                enemies.add(Enemy(context, size.x, size.y, 2, ligne_spawn[i]))
                nbre_enemies++
            }
            3 -> for (i in 0 until nbr_ennemis_spawn) {
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
                var final_i = 0
                for (i in 0 until ship.vie){
                    canvas.drawBitmap(hpBitmap,5f + i*size.y.toFloat()/11,5f,null)
                    final_i++
                }
                if (ship.shield == 1) {
                    canvas.drawBitmap(shieldBitmap,5f + final_i*size.y.toFloat()/11,5f,null)
                }
            }
            // Now draw the player spaceship
            canvas.drawBitmap(ship.Vbitmap, ship.position.left,
                ship.position.top,null)

            canvas.drawBitmap(upBitmap,20f,size.y.toFloat() - (2 * size.y / 11),null)
            canvas.drawBitmap(downBitmap,2*size.y.toFloat() /11 + 20f,size.y.toFloat() - (2 * size.y / 11),null)
            canvas.drawBitmap(pewBitmap,size.x.toFloat() - 2*size.y/11 - 20f,size.y.toFloat() - (2 * size.y / 11),null)

            // Draw the score
            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 50f
            canvas.drawText("Score: $score", 8.2f*size.x.toFloat()/10f, 75f, paint)

            holder.unlockCanvasAndPost(canvas)

        }
    }

    fun resume() {
        playing = true
        jeuxThread.start()
    }

    var typemobMulti = 0

    var isMusicOn = false

    override fun run() {
        var fps: Long = 1
        if (campainMusic != null){isMusicOn = true}
        while (playing){
            // Capture the current time
            val startFrameTime = System.currentTimeMillis()

            // Update the frame
            if (!paused) {
                update(fps)
                trashCollector()
            }

            if (typemob == 1 && score >= 500 + 1500*typemobMulti){
                typemob++
                BackgroundNumber++
                if (isMusicOn){
                    campainMusic!!.stop()
                    bossMusic = MediaPlayer.create(context, R.raw.pew_pew_boss_music)
                    bossMusic!!.isLooping = true
                    bossMusic!!.start()
                }
            }
            if (typemob ==2 && score >= 1000 + 1500*typemobMulti){
                typemob++
                BackgroundNumber++
                if (isMusicOn) {
                    bossMusic!!.stop()
                    campainMusic = MediaPlayer.create(context, R.raw.pew_pew_music_campagne)
                    campainMusic!!.isLooping = true
                    campainMusic!!.start()
                }
            }
            if (typemob == 3 && score >= 1500 + 1500*typemobMulti){
                typemob = 1
                BackgroundNumber = 0
                typemobMulti++
                if (isMusicOn) {
                    campainMusic!!.stop()
                    bossMusic = MediaPlayer.create(context, R.raw.pew_pew_boss_music)
                    bossMusic!!.isLooping = true
                    bossMusic!!.start()
                }
            }

            draw()

            // Calculate the fps rate this frame
            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if(timeThisFrame >= 1) {fps = 1000 / timeThisFrame}

        }
    }

    fun trashCollector(){
        var laserSafeRemove = lasers.toMutableList()
        for (laser in lasers) {
            if(!laser.visible){
                laserSafeRemove.remove(laser)
            }
        }
        lasers = laserSafeRemove as ArrayList<Laser>

        if(laserSafeAdd.isNotEmpty()){
            for(laser in laserSafeAdd){
                lasers.add(laser)
            }
            laserSafeAdd.clear()
        }


        var enemySafeRemove = enemies.toMutableList()
        for (enemy in enemies) {
            if(!enemy.visible){
                score += enemy.points
                enemySafeRemove.remove(enemy)
            }
        }
        enemies = enemySafeRemove as ArrayList<Enemy>
    }

    fun update(fps : Long) {
        if (enemies.isEmpty()||enemies[enemies.size-1].position.right < 3f*size.x/4f){
            spawn()
        }

        for (enemy in enemies) {
            enemy.update(fps)
        }
        for (laser in lasers) {
            laser.update(fps,"ship",enemies,missiles,ship)
        }
    }

    var pewSound : MediaPlayer? = null
    fun pewSound () {
        pewSound = MediaPlayer.create(context, R.raw.pew)
        pewSound!!.isLooping = false
        pewSound!!.start()
    }



    var clic = true

    fun pause() {
        playing = false
        shipligne = 6
        try {
            jeuxThread.interrupt()
        } catch (e: InterruptedException) {
            Log.e("Error:", "interrupting thread")
        }
    }

    val laserSafeAdd = ArrayList<Laser>()

    var lastPewTime = System.currentTimeMillis()
    var newPewTime = System.currentTimeMillis()


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
                        newPewTime = System.currentTimeMillis()
                        if (newPewTime - lastPewTime >= 600){
                            lastPewTime = newPewTime
                            if (pewSound != null){
                                if (pewSound!!.isPlaying){
                                    pewSound!!.pause()
                                }
                            }
                            pewSound()
                            clic = false

                            laserSafeAdd.add(Laser(context,size.x,size.y, shipligne))
                        }
                    }
                }
            }

            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_UP -> clic = true
        }
        return true
    }



}








