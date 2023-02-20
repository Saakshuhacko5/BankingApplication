package com.example.bankingapplication

import AppConstant
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bankingapplication.databinding.ActivityCredentialsBinding
import com.google.android.material.tabs.TabLayout

class CredentialsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCredentialsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCredentialsBinding.inflate(layoutInflater)
        val view = binding.root
        initialise()
        setContentView(view)
    }

    private fun initialise() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.log_in))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.sign_up))
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_about) {
            displayHelpDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayHelpDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.help).setMessage(AppConstant.MESSAGE)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //TODO: NOTE: This code can sometimes run multiple times if login is pressed quickly in succession
    fun login() {
        val intent = Intent(applicationContext, DrawerActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun profileCreated(bundle: Bundle?) {
        Toast.makeText(this, R.string.account_success, Toast.LENGTH_SHORT).show()
        login()
    }


}