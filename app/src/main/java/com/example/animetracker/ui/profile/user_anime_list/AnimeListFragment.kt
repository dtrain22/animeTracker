package com.example.animetracker.ui.profile.user_anime_list

import android.os.Bundle
import android.util.Log
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
import com.example.animetracker.ui.profile.update_list_fragment.UpdateListFragment


class AnimeListFragment : Fragment(), AnimeListAdapter.OnListListener {

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
            val mAdapter = AnimeListAdapter(userList, this)

            rvAnimeList.adapter = mAdapter

            rvAnimeList.itemAnimator = DefaultItemAnimator()
        })

        viewModel.getUserList()
    }

    override fun onClick(position: Int) {
        Log.d("select title", viewModel.userList.value?.get(position)?.title!!)
        val updateListFragment = UpdateListFragment()
        val fragmentManager = this.parentFragmentManager
        val argument = getInformationForListElement(position)
        updateListFragment.arguments = argument
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, updateListFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun getInformationForListElement(position: Int): Bundle {
        val bundle = Bundle()
        val mediaElement = viewModel.userList.value?.get(position)

        bundle.putInt("mediaID", mediaElement?.mediaId!!)
        bundle.putString("title", mediaElement.title)
        bundle.putString("status", mediaElement.status.toString())
        bundle.putInt("progress", mediaElement.progress!!)
        bundle.putInt("score", mediaElement.score!!.toInt())
        if(mediaElement.totalEpisodes != null) {
            bundle.putInt("totalEpisodes", mediaElement.totalEpisodes!!)
        }

        return bundle
    }
}
