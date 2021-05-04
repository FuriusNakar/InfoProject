package com.example.infoproject


import android.graphics.Point
import android.media.MediaPlayer
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.start_screen.*


class PewPewActivity : AppCompatActivity(), View.OnClickListener {
    //liste des images pour chaque type de vaisseau
    private val spaceship_list = intArrayOf(R.drawable.spaceship_blue,R.drawable.spaceship_green,R.drawable.spaceship_red)
    private val xwing_list = intArrayOf(R.drawable.xwing_blue,R.drawable.xwing_green, R.drawable.xwing_red)
    private val vargur_list = intArrayOf(R.drawable.vargur_blue,R.drawable.vargur_green, R.drawable.vargur_red)

    //liste des listes de vaisseaux
    private val test_shiplist = arrayListOf(spaceship_list, xwing_list,vargur_list)




    //liste d'images pour le choix des couleurs
    private val list_color = intArrayOf(R.drawable.pentagone_bleu, R.drawable.pentagone_vert, R.drawable.pentagone_rouge)
    //définition du vaisseau de base comme étant celui de la première couleur et du premier type ("spaceship_blue")
    private var id_ship = 0
    private var couleur_id = 0

    companion object{
        //Création de la variable définisant le skin du vaisseau (variable devant être accessible dans d'autres classes)
        var Vso = 0
        //Création des 2 variables s'occupant de la musique devant être accessible dans le PewPewView également
        var campaignMusic : MediaPlayer? = null
        var bossMusic : MediaPlayer? = null
    }

    init {
        //Définition du skin du vaisseau de base comme étant du premier type et de la première couleur
        Vso = test_shiplist[id_ship][couleur_id]
    }

    //Création du MediaPlayer jouant le son survenant lorsqu'on lance la partie
    private var grantedSound : MediaPlayer? = null

    private var PewPewView: PewPewView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        previous_shiptype.setOnClickListener(this)
        next_shiptype.setOnClickListener(this)
        previous_shipcolor.setOnClickListener(this)
        next_shipcolor.setOnClickListener(this)

        //start_button onclicklistener qui lance le jeu, PewPewView, et qui joue le granted sound
        start_button.setOnClickListener{
            grantedSound = MediaPlayer.create(this, R.raw.access_granted_sound)
            grantedSound!!.isLooping = false
            grantedSound!!.start()
            while (grantedSound!!.isPlaying) {}

            // Rend les détails de l'écran accessibles
            val display = windowManager.defaultDisplay
            // Charge la résolution de l'écran. Obtention de la taille

            val size = Point()
            display.getSize(size)

            // Initialise le fichier de vue du jeu et défini le contenu de l'écran
            PewPewView = PewPewView(this, size)
            setContentView(PewPewView)
            if (PewPewView?.playing == true){
                PewPewView?.playing = false
                PewPewView?.jeuxThread?.join()
            }
            PewPewView?.playing = true
            PewPewView?.jeuxThread?.start()

        }

    }


    // Joue la musique et change l'image du bouton de musique
    fun playSound(v: View?) {
        campaignMusic = MediaPlayer.create(this, R.raw.pew_pew_music_campagne)
        campaignMusic!!.isLooping = true
        campaignMusic!!.start()

        stopButton.isClickable = true
        stopButton.alpha = 0.5f
        playButton.isClickable = false
        playButton.alpha = 0f
    }

    // Arrête la musique et change l'image du bouton de musique
    fun stopSound(v: View?) {
        if (campaignMusic != null) {
            campaignMusic!!.stop()
            campaignMusic!!.release()
            campaignMusic = null
        }

        stopButton.isClickable = false
        stopButton.alpha = 0f
        playButton.isClickable = true
        playButton.alpha = 0.5f
    }

    // Arrête la musique quand le jeu est fermé
    override fun onStop() {
        super.onStop()
        if (campaignMusic != null) {
            campaignMusic!!.release()
            campaignMusic = null
        }
        if (bossMusic != null) {
            bossMusic!!.release()
            bossMusic = null
        }
        PewPewView?.pause()
    }

    override fun onPause() {
        super.onPause()
        if (campaignMusic != null) {
            campaignMusic!!.release()
            campaignMusic = null
        }
        if (bossMusic != null) {
            bossMusic!!.release()
            bossMusic = null
        }
        PewPewView?.pause()
    }

    //Fonction permetant de donner des actions aux boutons du xml start_screen
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.previous_shiptype ->
                    if (id_ship == 0){
                        id_ship = test_shiplist.size - 1
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        ship_png.colorFilter = null
                        Vso = test_shiplist[id_ship][couleur_id]
                    }
                    else {
                        id_ship --
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        Vso = test_shiplist[id_ship][couleur_id]
                    }


                R.id.next_shiptype->
                    if (id_ship == test_shiplist.size - 1){
                        id_ship = 0
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        ship_png.colorFilter = null
                        Vso = test_shiplist[id_ship][couleur_id]
                    }
                    else {
                        id_ship ++
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        Vso = test_shiplist[id_ship][couleur_id]
                    }


                R.id.previous_shipcolor ->
                    if (couleur_id == 0){
                        couleur_id = list_color.size - 1
                        color_png.setImageResource(list_color[couleur_id])
                        color_png.colorFilter = null
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        Vso = test_shiplist[id_ship][couleur_id]
                    }
                    else {
                        couleur_id --
                        color_png.setImageResource(list_color[couleur_id])
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        Vso = test_shiplist[id_ship][couleur_id]
                    }



                R.id.next_shipcolor ->
                    if (couleur_id == list_color.size - 1){
                        couleur_id = 0
                        color_png.setImageResource(list_color[couleur_id])
                        color_png.colorFilter = null
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        Vso = test_shiplist[id_ship][couleur_id]
                    }
                    else {
                        couleur_id ++
                        color_png.setImageResource(list_color[couleur_id])
                        ship_png.setImageResource(test_shiplist[id_ship][couleur_id])
                        color_png.colorFilter = null
                        Vso = test_shiplist[id_ship][couleur_id]
                    }


            }
        }

    }

}