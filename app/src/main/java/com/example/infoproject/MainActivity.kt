package com.example.infoproject


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.start_screen.*


class PewPewActivity : AppCompatActivity(), View.OnClickListener {

    val spaceship_list = intArrayOf(R.drawable.spaceship_blue,R.drawable.spaceship_green,R.drawable.spaceship_red)
    val xwing_list = intArrayOf(R.drawable.xwing_blue,R.drawable.xwing_green, R.drawable.xwing_red)
    val vargur_list = intArrayOf(R.drawable.vargur_blue,R.drawable.vargur_green, R.drawable.vargur_red)

    //liste dans une liste, sur python c'était comme ca
    val test_shiplist = arrayListOf(spaceship_list, xwing_list,vargur_list)




    //val list_ship = intArrayOf(R.drawable.spaceship_blue, R.drawable.xwing_blue)
    val list_color = intArrayOf(R.drawable.pentagone_bleu, R.drawable.pentagone_vert, R.drawable.pentagone_rouge)
    //val is_ship de base = list_ship[0]
    var id_ship = 0
    var couleur_id = 0

    var campainMusic : MediaPlayer? = null
    var grantedSound : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        previous_shiptype.setOnClickListener(this)
        next_shiptype.setOnClickListener(this)
        previous_shipcolor.setOnClickListener(this)
        next_shipcolor.setOnClickListener(this)
        

        //start_button onclicklistener qui lance une deuxième activité de jeux (pewpewview)
        start_button.setOnClickListener{
            grantedSound = MediaPlayer.create(this, R.raw.access_granted_sound)
            grantedSound!!.isLooping = false
            grantedSound!!.start()
            while (grantedSound!!.isPlaying) {}

            val jeux = Intent(this, JeuxActivity::class.java)

            startActivity(jeux)









        }
        //Score_text à uptade avec pewpewview

    }

    // Play campainMusic and switch button
    fun playSound(view: View) {
        if (campainMusic == null) {
            campainMusic = MediaPlayer.create(this, R.raw.pew_pew_music_campagne)
            campainMusic!!.isLooping = true
            campainMusic!!.start()
        } else campainMusic!!.start()
        playButton.isClickable = false
        playButton.alpha = 0f
        stopButton.isClickable = true
        stopButton.alpha = 0.5f
    }

    // Stop campainMusic and switch button
    fun stopSound(view: View) {
        if (campainMusic != null) {
            campainMusic!!.stop()
            campainMusic!!.release()
            campainMusic = null
        }
        playButton.isClickable = true
        playButton.alpha = 0.5f
        stopButton.isClickable = false
        stopButton.alpha = 0f
    }

    // Stops Music when the app is closed
    override fun onStop() {
        super.onStop()
        if (campainMusic != null) {
            campainMusic!!.release()
            campainMusic = null
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