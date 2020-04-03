package com.example.animetracker.ui.profile.user_profile


import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

import com.example.animetracker.R
import com.example.animetracker.UserQuery
import com.example.animetracker.data.models.UserModel
import com.example.animetracker.data.network.ApolloConnector
import com.example.animetracker.data.network.queries.UserQueries
import com.example.animetracker.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*
import okhttp3.OkHttpClient
import java.util.*

private val BASE_URL = "https://graphql.anilist.co"

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

        viewModel.userData.observe(viewLifecycleOwner, Observer<UserModel> {model: UserModel ->
            userName.text = model.name
            Picasso.get().load(model.avatar).into(profileAvatar)
        })

        viewModel.updateUserData()

    }
}
