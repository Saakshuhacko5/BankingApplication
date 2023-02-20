package com.example.bankingapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bankingapplication.CredentialsSegment.LoginFragment
import com.example.bankingapplication.CredentialsSegment.SignUpFragment

class ViewPagerAdapter(supportFragmentManager: FragmentManager, life: Lifecycle) :
    FragmentStateAdapter(supportFragmentManager, life) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            SignUpFragment()
        } else LoginFragment()
    }
}
