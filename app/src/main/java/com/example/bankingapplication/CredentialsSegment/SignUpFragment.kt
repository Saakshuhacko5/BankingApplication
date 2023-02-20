package com.example.bankingapplication.CredentialsSegment

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
import com.example.bankingapplication.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.signupButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.signup_button -> {
                createProfile()
            }
        }
    }

    private fun createProfile() {
        val applicationDb = ApplicationDB(requireActivity().applicationContext)
        val profiles: ArrayList<Profile> = applicationDb.allProfiles
        var usernameTaken = false
        for (iProfile in profiles.indices) {
            if (binding.userName.text.toString() == profiles[iProfile].username) {
                usernameTaken = true
            }
        }
        if (binding.firstName.text.toString() == "" || binding.lastName.text
                .toString() == "" || binding.country.text.toString() == "" || binding.userName.text
                .toString() == "" || binding.signupPassword.text
                .toString() == "" || binding.signupConfirm.text.toString() == ""
        ) {
            Toast.makeText(activity, R.string.fields_blank, Toast.LENGTH_SHORT).show()
        } else if (binding.signupPassword.text.toString() != binding.signupConfirm.text.toString()) {
            Toast.makeText(activity, R.string.password_mismatch, Toast.LENGTH_SHORT).show()
        } else if (usernameTaken) {
            Toast.makeText(activity, "A User has already taken that username", Toast.LENGTH_SHORT)
                .show()
        } else {
            val userProfile = Profile(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.country.text.toString(),
                binding.userName.text.toString(),
                binding.signupPassword.text.toString()
            )
            applicationDb.saveNewProfile(userProfile)
            val bundle = Bundle()
            bundle.putString(getString(R.string.username), userProfile.username)
            bundle.putString(getString(R.string.password), userProfile.password)
            (activity as CredentialsActivity?)?.profileCreated(bundle)
        }
    }

}