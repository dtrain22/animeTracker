package com.example.animetracker.ui.anime.anime_page

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.animetracker.R
import com.example.animetracker.data.models.AnimePageModel
import com.example.animetracker.data.models.UserModel
import com.example.animetracker.type.MediaFormat
import com.example.animetracker.ui.profile.update_list_fragment.UpdateListFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.anime_page_fragment.*
import kotlinx.android.synthetic.main.anime_page_fragment.status
import kotlinx.android.synthetic.main.anime_page_fragment.view.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.update_list_fragment.*
import java.text.NumberFormat

class AnimePageFragment : Fragment() {

    companion object {
        fun newInstance() =
            AnimePageFragment()
    }

    private lateinit var viewModel: AnimePageViewModel

    var mediaID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments

        mediaID = arguments?.getInt("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.anime_page_fragment, container, false)

        view.backButton.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack()
        }

        view.editEntryButton.setOnClickListener{
            val updateListFragment = UpdateListFragment()
            val fragmentManager = this.parentFragmentManager
            val argument = getInformation()
            updateListFragment.arguments = argument
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, updateListFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.description.movementMethod = ScrollingMovementMethod()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnimePageViewModel::class.java)

        viewModel.animeEntry.observe(viewLifecycleOwner, Observer<AnimePageModel> { animeModel: AnimePageModel ->
            updateView(animeModel)
        })

        viewModel.fetchAnimeInfo(mediaID!!)
    }

    private fun updateView(animePageModel: AnimePageModel) {
        Picasso.get().load(animePageModel.coverImage).into(coverImage)
        animeTitle.text = animePageModel.title
        status.text = animePageModel.status.toString()
        meanScore.text = ("Mean Score: " + (animePageModel.meanScore?.div(10)))
        popularity.text = ("Total Users: " + NumberFormat.getInstance().format(animePageModel.popularity))
        description.text = animePageModel.description

        if (animePageModel.format == MediaFormat.TV || animePageModel.format == MediaFormat.TV_SHORT || animePageModel.format == MediaFormat.ONA || animePageModel.format == MediaFormat.OVA) {
            episodes.text = ("Total Episodes: " + animePageModel.episodes)
        }

        if (animePageModel.season != null && animePageModel.seasonYear != null) {
            season.text = (animePageModel.season + " " + animePageModel.seasonYear)
        }
    }

    private fun getInformation(): Bundle {
        val bundle = Bundle()
        val mediaElement = viewModel.animeEntry.value

        bundle.putInt("mediaID", mediaID!!)
        bundle.putString("title", mediaElement?.title)
        if(mediaElement?.episodes != null) {
            bundle.putInt("totalEpisodes", mediaElement.episodes!!)
        }

        return bundle
    }
}
