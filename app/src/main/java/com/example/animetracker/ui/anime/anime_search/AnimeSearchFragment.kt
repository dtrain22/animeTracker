package com.example.animetracker.ui.anime.anime_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.animetracker.R
import com.example.animetracker.adapter.AnimeSearchAdapter
import com.example.animetracker.ui.anime.anime_page.AnimePageFragment
import kotlinx.android.synthetic.main.anime_search_fragment.*
import kotlinx.android.synthetic.main.anime_search_fragment.view.searchAnime

class AnimeSearchFragment : Fragment(), AnimeSearchAdapter.OnListListener {

    companion object {
        fun newInstance() = AnimeSearchFragment()
    }

    private lateinit var viewModel: AnimeSearchViewModel

    private lateinit var rvAnimeSearch: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.anime_search_fragment, container, false)

        rvAnimeSearch = view.findViewById<View>(R.id.rvSearchAnime) as RecyclerView

        rvAnimeSearch.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.searchAnime.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val search = searchAnime.text.toString()
                viewModel.searchAnime(search)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnimeSearchViewModel::class.java)

        viewModel.animeSearch.observe(viewLifecycleOwner, Observer {searchResults ->
            val mAdapter = AnimeSearchAdapter(searchResults,this)

            rvAnimeSearch.adapter = mAdapter

            rvAnimeSearch.itemAnimator = DefaultItemAnimator()
        })
    }

    override fun onClick(position: Int) {
        val animePageFragment = AnimePageFragment()
        val fragmentManager = this.parentFragmentManager
        val arguments = Bundle()
        arguments.putInt("id", viewModel.animeSearch.value?.get(position)?.id!!)
        animePageFragment.arguments = arguments
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, animePageFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
