package com.example.animetracker.ui.profile.user_anime_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animetracker.R
import com.example.animetracker.adapter.AnimeListAdapter
import com.example.animetracker.data.models.AnimeListModel


class AnimeListFragment : Fragment() {

    companion object {
        fun newInstance() =
            AnimeListFragment()
    }

    private lateinit var viewModel: AnimeListViewModel

    private lateinit var rvAnimeList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.anime_list_fragment, container, false)
        // 1. get a reference to recyclerView
        // 1. get a reference to recyclerView
        rvAnimeList = rootView.findViewById<View>(R.id.rvAnimeList) as RecyclerView

        rvAnimeList.layoutManager = LinearLayoutManager(activity)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnimeListViewModel::class.java)

        viewModel.userList.observe(viewLifecycleOwner, Observer { userList: MutableList<AnimeListModel> ->
            val mAdapter = AnimeListAdapter(userList)

            rvAnimeList.adapter = mAdapter

            rvAnimeList.itemAnimator = DefaultItemAnimator()
        })

        viewModel.updateUserList()
    }

}
