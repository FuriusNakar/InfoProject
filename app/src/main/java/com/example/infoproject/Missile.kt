package com.example.infoproject

import android.graphics.Rect

class Missile (ScreenX : Float, ScreenY : Float){

    var speed = 100f


    fun fait_degat(){
        if posistion.intersect()
    }

    fun update(interval: Double) {
        if (section>=0 && section<CIBLE_PIECES && !cibleTouchee[section]) {
            cibleTouchee[section] = true
            balle.resetCanonBall()
            view.increaseTimeLeft()

        }
        if (canonballOnScreen) {
            canonball.x += (interval * canonballVitesseX).toFloat()
            canonball.y += (interval * canonballVitesseY).toFloat()

            /* Vérifions si la balle touche l'obstacle ou pas */
            if (canonball.x + canonballRadius>obstacle.obstacle.left
                && canonball.y+canonballRadius>obstacle.obstacle.top
                && canonball.y-canonballRadius<obstacle.obstacle.bottom) {
                canonballVitesseX *= -1
                canonball.offset((3*canonballVitesseX*interval).toFloat(),0f)
            }
            // Si elle sorte de l'écran
            else if (canonball.x + canonballRadius > view.screenWidth
                || canonball.x - canonballRadius < 0) {
                canonballOnScreen = false
            }
            else if (canonball.y + canonballRadius > view.screenHeight
                || canonball.y - canonballRadius < 0) {
                canonballOnScreen = false
            }
            else if (canonball.x+canonballRadius>cible.cible.left
                && canonball.y+canonballRadius>cible.cible.top
                && canonball.y-canonballRadius<cible.cible.bottom) {
                cible.detectChoc(this)
                /* Pour l'instant rien ne se passe lorsque balle heurte la cible */
            }
        }
    }
}

