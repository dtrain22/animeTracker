package com.example.animetracker.ui.profile.update_list_fragment

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.animetracker.R
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.type.MediaListStatus
import com.example.animetracker.ui.profile.user_anime_list.AnimeListFragment
import com.example.animetracker.utility.InputFilterMinMax
import kotlinx.android.synthetic.main.update_list_fragment.*
import kotlinx.android.synthetic.main.update_list_fragment.view.*

class UpdateListFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateListFragment()
    }

    private lateinit var viewModel: UpdateListViewModel

    val entry = AnimeListModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments

        entry.mediaId = arguments?.getInt("mediaID")
        entry.title = arguments?.getString("title")
        if (arguments?.getString("status") != null) {
            entry.status = MediaListStatus.valueOf(arguments?.getString("status")!!)
        }
        entry.progress = arguments?.getInt("progress")
        entry.totalEpisodes = arguments?.getInt("totalEpisodes")
        entry.score = arguments?.getInt("score")?.toDouble()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.update_list_fragment, container, false)

        ArrayAdapter.createFromResource(activity!!.baseContext,
            R.array.status_array,
            R.layout.support_simple_spinner_dropdown_item
        ). also {arrayAdapter ->  
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            view.statusSpinner.adapter = arrayAdapter
            if (entry.status != null) {
                val spinnerPosition: Int = arrayAdapter.getPosition(entry.status.toString())
                view.statusSpinner.setSelection(spinnerPosition)
            }
        }

        view.scoreEntry.filters = arrayOf<InputFilter>(InputFilterMinMax(1, 10))
        if (entry.totalEpisodes != 0) {
            view.progressEntry.filters = arrayOf<InputFilter>(InputFilterMinMax(0, entry.totalEpisodes!!))
            view.totalEpisodes.text = ("/ " + entry.totalEpisodes)
        } else {
            view.totalEpisodes.text = ("/ ?")
        }

        view.scoreEntry.setText(entry.score!!.toInt().toString())
        view.progressEntry.setText(entry.progress!!.toString())

        view.cancelButton.setOnClickListener {
            returnToAnimeList()
        }

        view.updateButton.setOnClickListener {
            entry.status = MediaListStatus.valueOf(statusSpinner.selectedItem.toString())
            entry.totalEpisodes = Integer.parseInt(scoreEntry.text.toString())
            val score = Integer.parseInt(scoreEntry.text.toString())
            viewModel.updateUserList(entry,score)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.text = (entry.title)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateListViewModel::class.java)

        viewModel.listUpdated.observe(viewLifecycleOwner, Observer { success: Boolean ->
            if(success) {
                returnToAnimeList()
            }
        })
    }

    private fun returnToAnimeList(){
        val fragmentManager = parentFragmentManager
        fragmentManager.popBackStack()
    }
}
