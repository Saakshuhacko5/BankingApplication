package com.example.bankingapplication.CredentialsSegment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bankingapplication.CredentialsActivity
import com.example.bankingapplication.Model.Profile
import com.example.bankingapplication.Model.db.ApplicationDB
import com.example.bankingapplication.R
import com.example.bankingapplication.databinding.FragmentLoginBinding
import com.google.gson.Gson

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private var username: String = ""
    private var password: String = ""
    private lateinit var lastProfileUsed: Profile
    private lateinit var gson: Gson
    private var json: String = ""
    private lateinit var userPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setOnClickListeners() {
        binding.loginButton.setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        setupViews()
        setOnClickListeners()

        binding.userName.setText(username)
        binding.loginPassword.setText(password)
        binding.chkRemember.isChecked = true

        return binding.root
    }


    private fun setupViews() {
        userPreferences =
            requireActivity().getSharedPreferences("LastProfileUsed", Context.MODE_PRIVATE)
        binding.chkRemember.isChecked = userPreferences.getBoolean("rememberMe", false)
        if (binding.chkRemember.isChecked) {
            gson = Gson()
            json = userPreferences.getString("LastProfileUsed", "").toString()
            lastProfileUsed = gson.fromJson(json, Profile::class.java)
            binding.userName.setText(lastProfileUsed.username)
            binding.loginPassword.setText(lastProfileUsed.password)

            //Automatic login for user
            //login();
            //finish();
        }
    }
//    override fun onStop() {
//        if (binding.userName.getText()
//                .toString() == lastProfileUsed.username && binding.userName.getText()
//                .toString() == lastProfileUsed.password
//        ) {
//            userPreferences.edit().putBoolean("rememberMe", binding.chkRemember.isChecked()).apply()
//        } else {
//            userPreferences.edit().putBoolean("rememberMe", false).apply()
//        }
//        super.onStop()
//    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.login_button -> {
                validateAccount()
            }

        }
    }

    private fun validateAccount() {
        val applicationDB = ApplicationDB(requireContext().applicationContext)
        val profiles: ArrayList<Profile> = applicationDB.allProfiles
        var match = false
        if (profiles.size > 0) {
            for (i in profiles.indices) {
                if (binding.userName.getText()
                        .toString() == profiles[i].getUsername() && binding.loginPassword.getText()
                        .toString() == profiles[i].getPassword()
                ) {
                    match = true
                    userPreferences.edit().putBoolean(
                        (R.string.remember_me).toString(),
                        binding.chkRemember.isChecked()
                    ).apply()
                    lastProfileUsed = profiles[i]
                    val prefsEditor: SharedPreferences.Editor = userPreferences.edit()
                    gson = Gson()
                    json = gson.toJson(lastProfileUsed)
                    prefsEditor.putString("LastProfileUsed", json).apply()
                    (activity as CredentialsActivity?)?.login()
                }
            }
            if (!match) {
                Toast.makeText(activity, R.string.incorrect_login, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, R.string.incorrect_login, Toast.LENGTH_SHORT).show()
        }
    }


}