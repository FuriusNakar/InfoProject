package com.example.infoproject

import android.app.Activity
import android.content.Intent
import android.graphics.Point
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View

import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.start_screen.*


abstract class JeuxActivity : AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get a Display object to access screen details
        val display = windowManager.defaultDisplay
        // Load the resolution into a Point object
        val size = Point()
        display.getSize(size)

        // Initialize gameView and set it as the view
        val PewPewView = PewPewView(this, size)
        setContentView(PewPewView)

    }


    // This method executes when the player starts the game
    override fun onResume() {
        super.onResume()

        // Tell the gameView resume method to execute
        //PewPewView?.resume()
    }

    // This method executes when the player quits the game
    override fun onPause() {
        super.onPause()

        // Tell the gameView pause method to execute
        //PewPewView?.pause()
    }
    }
