package com.example.infoproject

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.start_screen.*


class PewPewActivity : AppCompatActivity(), View.OnClickListener {

    val spaceship_list = intArrayOf(R.drawable.spaceship_blue,R.drawable.spaceship_green,R.drawable.spaceship_rouge)
    val xwing_list = intArrayOf(R.drawable.xwing_blue,R.drawable.xwing_green, R.drawable.xwing_red)

    //liste dans une liste, sur python c'était comme ca
    val test_shiplist = arrayListOf(spaceship_list, xwing_list)


    //val list_ship = intArrayOf(R.drawable.spaceship_blue, R.drawable.xwing_blue)
    val list_color = intArrayOf(R.drawable.pentagone_bleu, R.drawable.pentagone_vert, R.drawable.pentagone_rouge)
    //val is_ship de base = list_ship[0]
    var id_ship = 0
    var couleur_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)
        previous_shiptype.setOnClickListener(this)
        next_shiptype.setOnClickListener(this)
        previous_shipcolor.setOnClickListener(this)
        next_shipcolor.setOnClickListener(this)


        //start_button onclicklistener qui lance une deuxième activité de jeux (pewpewview)
        start_button.setOnClickListener{
            setContentView(R.layout.activity_main)

            //val jeux = Intent(this, JeuxActivity::class.java)
            //startActivity(jeux)
        }
        //Score_text à uptade avec pewpewview

    }

    var campagneMediaPlayer: MediaPlayer? = null

    // Play Music and switch buttons
    fun playSound(view: View) {
        if (campagneMediaPlayer == null) {
            campagneMediaPlayer = MediaPlayer.create(this, R.raw.pew_pew_music_campagne)
            campagneMediaPlayer!!.isLooping = true
            campagneMediaPlayer!!.start()
        } else campagneMediaPlayer!!.start()
        playButton.alpha = 0f
        playButton.isClickable = false
        stopButton.alpha = 0.5f
        stopButton.isClickable = true
    }

    // Stop Music and switch buttons
    fun stopSound(view: View) {
        if (campagneMediaPlayer != null) {
            campagneMediaPlayer!!.stop()
            campagneMediaPlayer!!.release()
            campagneMediaPlayer = null
        }
        playButton.alpha = 0.5f
        playButton.isClickable = true
        stopButton.alpha = 0f
        stopButton.isClickable = false
    }

    // Stop music when the app is closed
    override fun onStop() {
        super.onStop()
        if (campagneMediaPlayer != null) {
            campagneMediaPlayer!!.release()
            campagneMediaPlayer = null
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.previous_shiptype ->
                    if (id_ship == 0){
                        id_ship = test_shiplist.size - 1
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        ship_png.colorFilter = null
                    }
                    else {
                        id_ship --
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                    }


                R.id.next_shiptype->
                    if (id_ship == test_shiplist.size - 1){
                        id_ship = 0
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        ship_png.colorFilter = null
                    }
                    else {
                        id_ship ++
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                    }


                R.id.previous_shipcolor ->
                    if (couleur_id == 0){
                        couleur_id = list_color.size - 1
                        color_png.setImageResource(list_color[couleur_id])
                        color_png.colorFilter = null
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                    }
                    else {
                        couleur_id --
                        color_png.setImageResource(list_color[couleur_id])
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                    }


                R.id.next_shipcolor ->
                    if (couleur_id == list_color.size - 1){
                        couleur_id = 0
                        color_png.setImageResource(list_color[couleur_id])
                        color_png.colorFilter = null
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                    }
                    else {
                        couleur_id ++
                        color_png.setImageResource(list_color[couleur_id])
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        color_png.colorFilter = null
                    }
            }
        }
    }
}



/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get a Display object to access screen details
        val display = windowManager.defaultDisplay
        // Load the resolution into a Point object
        val size = Point()
        display.getSize(size)

        // Initialize gameView and set it as the view
        PewPewView = PewPewView(this, size)
        setContentView(PewPewView)
    }

    // This method executes when the player starts the game
    override fun onResume() {
        super.onResume()

        // Tell the gameView resume method to execute
        PewPewView?.resume()
    }

    // This method executes when the player quits the game
    override fun onPause() {
        super.onPause()

        // Tell the gameView pause method to execute
        PewPewView?.pause()
    }
}

 */