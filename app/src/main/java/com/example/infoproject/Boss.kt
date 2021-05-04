package com.example.infoproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import java.util.ArrayList

class Boss(context : Context, private val ScreenX : Int, private val ScreenY : Int, ligne : Int, typeboss : Int, vieMulti : Int) {
    //Défini la vie du boss
    var vie = 5 + 5 * vieMulti

    //Donne les dimensions du boss
    private val largeur = ScreenY / 6f
    private val hauteur = ScreenY / 6f


    //Etabli la boite de collision du boss
    val position =
        RectF(
            ScreenX - largeur - 20f,
            ScreenY * ligne / 11f - hauteur/2 ,
            ScreenX - 20f,
            ScreenY * ligne /11f + hauteur/2
        )
    //Défini la vitesse du boss
    private var speed = 25f

    //Initialise la bitmap du boss
    lateinit var Bbitmap : Bitmap

    //Création de la liste de lignes sur lesquels le boss va tier
    private var shootingList = ArrayList<Int>()

    init{
        //Ajoute l'image correspondante au boss dans sa Bitmap
        when (typeboss){
            1 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alien_boss)
            2 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dragon_boss)
            3 -> Bbitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rooster)
        }

        //Redimentionne l'image du boss dans sa boite de collision
        Bbitmap = createScaledBitmap(Bbitmap, largeur.toInt(), hauteur.toInt(), false)


        //Initialise la liste des lignes où le boss va tirer
        shootingList.add((2..8).random())
        var intToAdd = (2..8).random()
        for (i in 0..2){
            while (intToAdd in shootingList){
                intToAdd = (2..8).random()
            }
            shootingList.add(intToAdd)
        }
    }

    //Défini la direction (Axe X) dans laquelle le boss va se déplacer lors de la collision avec le bord de la zone de jeu
    private var signOffset = -1

    //Variable définisant si le boss tire un missile ou non
    var isShooting = false

    //Variable définisant la ligne où le boss tire et donc où le missile apparaitra
    var ligneWhereShot = 0

    private var intToAdd = 0

    fun update(fps : Long){
        //Fonction se chargant du mouvement du boss et de vérifier si il tire
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
                if(position.left <= 5f * ScreenX / 10f){
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
        //Fonction appelée par le laser lors de son contact avec le boss
        vie -= 1
    }
}
