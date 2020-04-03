package com.example.animetracker.ui.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.animetracker.R

class AnimePageFragment : Fragment() {

    companion object {
        fun newInstance() = AnimePageFragment()
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
        viewModel = ViewModelProvider(this).get(AnimePageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
