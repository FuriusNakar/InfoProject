package com.example.infoproject

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.example.infoproject.PewPewActivity.Companion.bossMusic
import com.example.infoproject.PewPewActivity.Companion.campaignMusic
import kotlin.system.exitProcess


class PewPewView(context: Context, private val size: Point) : SurfaceView(context),
    Runnable {

    companion object {
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
    private var bosses = ArrayList<Boss>()

    private var nextBossMulti = 1

    private fun spawn(BossReinforcements : Boolean) {
        if (nbre_enemies <= 25 + 10 * (nextBossMulti) || BossReinforcements) {
            val ligne_spawn = arrayListOf<Int>()
            var inttoadd = (2..8).random()
            ligne_spawn.add(inttoadd)
            while (ligne_spawn.size < 7) {
                inttoadd = (2..8).random()
                var adding_int = true
                for (int in ligne_spawn) {
                    if (inttoadd == int) {
                        adding_int = false
                    }
                }
                if (adding_int) {
                    ligne_spawn.add(inttoadd)
                }
            }
            var nbr_ennemis_spawn = ligne_spawn[0] - 1
            if (nbr_ennemis_spawn < 4) {
                nbr_ennemis_spawn++
            } else if (nbr_ennemis_spawn > 4) {
                nbr_ennemis_spawn--
            }

            //fction de spawn d'enemy en fction du type
            for (i in 0 until nbr_ennemis_spawn) {
                enemies.add(Enemy(context, size.x, size.y, typemob, ligne_spawn[i]))
                nbre_enemies++
            }

        } else if (bosses.isEmpty()){
            bosses.add(Boss(context, size.x, size.y, 6, typemob, nextBossMulti))
        }

    }

    private var BackgroundNumber = 0

    private val Backgroundlist =
        intArrayOf(R.drawable.barren_planet, R.drawable.lava_planet, R.drawable.temperate_planet)

    private fun draw() {
        //fction responsable du dessiner les bitmaps sur imageview
        if (holder.surface.isValid) {
            var screenBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, Backgroundlist[BackgroundNumber])

            screenBitmap = Bitmap.createScaledBitmap(screenBitmap, size.x, size.y, false)

            var upBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.moving_up_arrow)
            upBitmap = Bitmap.createScaledBitmap(upBitmap, 2 * size.y / 11, 2 * size.y / 11, false)

            var downBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.moving_down_arrow)
            downBitmap =
                Bitmap.createScaledBitmap(downBitmap, 2 * size.y / 11, 2 * size.y / 11, false)

            var pewBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.fire_button)
            pewBitmap =
                Bitmap.createScaledBitmap(pewBitmap, 2 * size.y / 11, 2 * size.y / 11, false)

            var hpBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.pentagone_vert)
            hpBitmap = Bitmap.createScaledBitmap(hpBitmap, size.y / 11, size.y / 11, false)

            var shieldBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.pentagone_bleu)
            shieldBitmap = Bitmap.createScaledBitmap(shieldBitmap, size.y / 11, size.y / 11, false)

            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawBitmap(screenBitmap, 0f, 0f, null)

            if (lasers.isNotEmpty()) {
                for (laser in lasers) {
                    canvas.drawBitmap(laser.Bubitmap, laser.position.left, laser.position.top, null)
                }
            }

            if (enemies.isNotEmpty()) {
                for (enemy in enemies) {
                    canvas.drawBitmap(enemy.Ebitmap, enemy.position.left, enemy.position.top, null)
                }
            }

            if (bosses.isNotEmpty()) {
                canvas.drawBitmap(
                    bosses[0].Bbitmap,
                    bosses[0].position.left,
                    bosses[0].position.top,
                    null
                )
            }

            if (missiles.isNotEmpty()) {
                for (missile in missiles) {
                    canvas.drawBitmap(
                        missile.Bubitmap,
                        missile.position.left,
                        missile.position.top,
                        null
                    )
                }
            }


            if (ship.vie > 0) {
                var final_i = 0
                for (i in 0 until ship.vie) {
                    canvas.drawBitmap(hpBitmap, 5f + i * size.y.toFloat() / 11, 5f, null)
                    final_i++
                }
                if (ship.shield >= 1f) {
                    canvas.drawBitmap(shieldBitmap, 5f + final_i * size.y.toFloat() / 11, 5f, null)
                }
            }
            // Now draw the player spaceship
            canvas.drawBitmap(
                ship.Vbitmap, ship.position.left,
                ship.position.top, null
            )

            canvas.drawBitmap(upBitmap, 20f, size.y.toFloat() - (2 * size.y / 11), null)
            canvas.drawBitmap(
                downBitmap,
                2 * size.y.toFloat() / 11 + 20f,
                size.y.toFloat() - (2 * size.y / 11),
                null
            )
            canvas.drawBitmap(
                pewBitmap,
                size.x.toFloat() - 2 * size.y / 11 - 20f,
                size.y.toFloat() - (2 * size.y / 11),
                null
            )

            // Draw the score
            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 50f
            canvas.drawText("Score: $score", 8.2f * size.x.toFloat() / 10f, 75f, paint)

            holder.unlockCanvasAndPost(canvas)

        }
    }

    private var isMusicOn = false

    override fun run() {
        var fps: Long = 1
        if (campaignMusic != null) {
            isMusicOn = true
        }
        while (playing || !playing) {

            shipligne = 6
            score = 0
            ship = Ship(context, size.x, size.y)
            typemob = 1
            enemies.clear()
            nbre_enemies = 0
            lasers.clear()
            missiles.clear()
            bosses.clear()
            nextBossMulti = 1
            BackgroundNumber = 0

            while (playing) {
                // Capture the current time
                val startFrameTime = System.currentTimeMillis()

                // Update the frame
                if (!paused) {
                    update(fps)
                    trashCollector()
                }

                if (isMusicOn && bosses.isNotEmpty() && campaignMusic != null && bossMusic == null) {
                    bossMusic = MediaPlayer.create(context, R.raw.pew_pew_boss_music)
                    bossMusic!!.isLooping = true
                    bossMusic!!.start()
                    campaignMusic!!.stop()
                    campaignMusic = null
                }

                if (isMusicOn && bosses.isEmpty() && campaignMusic == null && bossMusic != null) {
                    campaignMusic = MediaPlayer.create(context, R.raw.pew_pew_music_campagne)
                    campaignMusic!!.isLooping = true
                    campaignMusic!!.start()
                    bossMusic!!.stop()
                    bossMusic = null
                }

                draw()

                // Calculate the fps rate this frame
                val timeThisFrame = System.currentTimeMillis() - startFrameTime
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame
                }
                if (fps < 1) {
                    fps = 1
                }
            }

            while (!playing) {
                gameOver()
            }
        }
    }

    private fun gameOver() {
        //fction responsable du dessiner les bitmaps sur imageview
        if (holder.surface.isValid) {
            var gameOverScreenBitmap: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.ice_planet)

            gameOverScreenBitmap =
                Bitmap.createScaledBitmap(gameOverScreenBitmap, size.x, size.y, false)

            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawBitmap(gameOverScreenBitmap, 0f, 0f, null)

            paint.color = Color.argb(255, 150, 150, 150)

            canvas.drawRect(2f * size.x / 10f, 4.5f * size.y / 10f,4f * size.x / 10f, 5.5f * size.y / 10f,paint)
            canvas.drawRect(6f * size.x / 10f, 4.5f * size.y / 10f,8f * size.x / 10f, 5.5f * size.y / 10f,paint)

            // Draw the score
            paint.color = Color.argb(255, 0, 0, 0)
            paint.textSize = 100f
            canvas.drawText("Score: $score", 4f * size.x.toFloat() / 10f, 7.5f * size.y / 10f, paint)

            // Draw Exit Button
            paint.textSize = 80f
            canvas.drawText("Exit", 2.7f * size.x.toFloat() / 10f, 5.25f * size.y / 10f, paint)

            // Draw Play Again Button
            paint.textSize = 80f
            canvas.drawText("Play Again", 6.2f * size.x.toFloat() / 10f, 5.25f * size.y / 10f, paint)

            // Draw Game Over text
            paint.color = Color.argb(255, 255, 0, 0)
            paint.textSize = 150f
            canvas.drawText("Game Over", 3.5f * size.x.toFloat() / 10f, 2.5f * size.y / 10f, paint)

            holder.unlockCanvasAndPost(canvas)

        }
    }

    private fun trashCollector() {
        val laserSafeRemove = lasers.toMutableList()
        for (laser in lasers) {
            if (!laser.visible) {
                laserSafeRemove.remove(laser)
            }
        }
        lasers = laserSafeRemove as ArrayList<Laser>

        val enemySafeRemove = enemies.toMutableList()
        for (enemy in enemies) {
            if (!enemy.visible) {
                enemySafeRemove.remove(enemy)
                if (enemy.points >= 0) {
                    score += enemy.points * nextBossMulti
                } else {
                    ship.degat()
                }
            }
        }
        enemies = enemySafeRemove as ArrayList<Enemy>

        val missileSafeRemove = missiles.toMutableList()
        for (missile in missiles) {
            if (!missile.visible) {
                missileSafeRemove.remove(missile)
            }
        }
        missiles = missileSafeRemove as ArrayList<Missile>

        if (bosses.isNotEmpty() && bosses[0].vie <= 0) {
            bosses.clear()
            if (typemob == 3) {
                typemob = 1
                BackgroundNumber = 0
            } else {
                BackgroundNumber++
                typemob++
            }
            score += 500 * nextBossMulti
            nextBossMulti++
            nbre_enemies = 0
        }

        if (ship.vie <= 0) {
            pause()
        }
    }

    var timeAnEnemyShot = System.currentTimeMillis()
    var currentTime = System.currentTimeMillis()

    private fun update(fps: Long) {
        ship.shieldRegeneration()

        for (laser in lasers) {
            laser.update(fps, "ship", enemies, missiles, ship, bosses)
        }

        for (missile in missiles) {
            missile.update(fps, typemob, enemies, missiles, ship, bosses)
        }

        for (enemy in enemies) {
            enemy.update(fps)
        }

        if (bosses.isNotEmpty()) {
            bosses[0].update(fps)
            if (bosses[0].isShooting) {
                for (i in 0..((1f/3f * (nextBossMulti-1))).toInt()){
                    missiles.add(
                        Missile(context,size.x,size.y,bosses[0].ligneWhereShot,typemob,bosses[0].position.left - i * size.y/20))
                }
            } else if (enemies.isEmpty() || enemies[enemies.size - 1].position.right < 2f * size.x / 3f) {
                spawn(true)
            }
        }

        currentTime = System.currentTimeMillis()
        if (enemies.isNotEmpty() && bosses.isEmpty() && missiles.isEmpty() && currentTime - timeAnEnemyShot >= (10000-(500 * (nextBossMulti-1)))){
            val enemyShootingNum = (0..(enemies.size - 1 )).random()
            if (enemies[enemyShootingNum].type == typemob && enemies[enemyShootingNum].visible){
                missiles.add(Missile(context,size.x,size.y,enemies[enemyShootingNum].lignePos,typemob,enemies[enemyShootingNum].position.left))
                timeAnEnemyShot = currentTime
            }
        }

        if (enemies.isEmpty() || enemies[enemies.size - 1].position.right < 4f * size.x / 5f) {
            spawn(false)
        }

        if (laserSafeAdd.isNotEmpty()) {
            for (laser in laserSafeAdd) {
                lasers.add(laser)
            }
            laserSafeAdd.clear()
        }
    }

    private var pewSound: MediaPlayer? = null
    private fun pewSound() {
        pewSound = MediaPlayer.create(context, R.raw.pew)
        pewSound!!.isLooping = false
        pewSound!!.start()
    }


    private var clic = true

    fun pause() {
        playing = false
        shipligne = 6
        try {
            jeuxThread.interrupt()
        } catch (e: InterruptedException) {
            Log.e("Error:", "interrupting thread")
        }
    }

    private val laserSafeAdd = ArrayList<Laser>()

    private var lastPewTime = System.currentTimeMillis()
    private var newPewTime = System.currentTimeMillis()


    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        if (playing) {
            val motionArea = size.y - (2 * size.y / 11)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                // Or moved their finger while touching screen
                MotionEvent.ACTION_POINTER_DOWN,
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE -> {
                    if (motionEvent.y > motionArea && clic) {
                        if (motionEvent.x > 20f && motionEvent.x < 2 * size.y / 11 + 20f) {
                            ship.bouge(1)
                            clic = false
                        } else if (motionEvent.x > 2 * size.y / 11 + 20f && motionEvent.x < 4 * size.y / 11 + 20f) {
                            ship.bouge(-1)
                            clic = false
                        } else if (motionEvent.x > size.x - 2 * size.y / 11 - 20f && motionEvent.x < size.x - 20f) {
                            newPewTime = System.currentTimeMillis()
                            if (newPewTime - lastPewTime >= 500) {
                                lastPewTime = newPewTime
                                pewSound()
                                clic = false

                                laserSafeAdd.add(Laser(context, size.x, size.y, shipligne))
                            }
                        }
                    }
                }

                MotionEvent.ACTION_POINTER_UP,
                MotionEvent.ACTION_UP -> clic = true
            }

        }

        if (!playing) {
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN,
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE -> {
                    if (motionEvent.y > 0.4f * size.y && motionEvent.y < 0.6f * size.y) {
                        if (motionEvent.x > 0.2f * size.x && motionEvent.x < 0.4f * size.x) {
                            exitProcess(-1)
                        }
                        if (motionEvent.x > 0.6f * size.x && motionEvent.x < 0.8f * size.x) {
                            playing = true
                        }
                    }
                }
            }

        }
        return true
    }
}








