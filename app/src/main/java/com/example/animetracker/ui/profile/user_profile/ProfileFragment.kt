package com.example.animetracker.ui.profile.user_profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.animetracker.R
import com.example.animetracker.data.models.UserModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() =
            ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.userData.observe(viewLifecycleOwner, Observer<UserModel> {userModel: UserModel ->
            userName.text = userModel.name
            Picasso.get().load(userModel.avatar).into(profileAvatar)
        })

        viewModel.updateUserData()

    }
}
