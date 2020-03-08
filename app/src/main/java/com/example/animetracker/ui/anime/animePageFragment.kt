package com.example.animetracker.ui.anime

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.animetracker.R

class animePageFragment : Fragment() {

    companion object {
        fun newInstance() = animePageFragment()
    }

    private lateinit var viewModel: AnimePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anime_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnimePageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
