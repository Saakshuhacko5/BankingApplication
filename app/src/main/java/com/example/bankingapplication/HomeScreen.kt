package com.example.bankingapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.bankingapplication.databinding.HomeScreenBinding

class HomeScreen : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: HomeScreenBinding
    private lateinit var anim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        initialise()
        setContentView(view)
    }

    private fun initialise() {
        anim = AnimationUtils.loadAnimation(this, R.anim.animation)
        binding.bankLogo.animation = anim
        binding.bankTitle.animation = anim
        fillCredentials()
    }

    private fun fillCredentials() {
        Handler().postDelayed(Runnable {
            val intent = Intent(this, CredentialsActivity::class.java)
            startActivity(intent)
            finish()
        }, AppConstant.SPLASH_SCREEN.toLong())

    }

    override fun onClick(p0: View?) {
    }
}