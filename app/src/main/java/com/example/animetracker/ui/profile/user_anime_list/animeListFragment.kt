package com.example.animetracker.ui.profile.user_anime_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.animetracker.R

class animeListFragment : Fragment() {

    companion object {
        fun newInstance() =
            animeListFragment()
    }

    private lateinit var viewModel: AnimeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anime_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnimeListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
