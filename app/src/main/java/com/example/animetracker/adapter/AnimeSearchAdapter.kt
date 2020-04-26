package com.example.animetracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animetracker.R
import com.example.animetracker.data.models.AnimeSearchModel
import com.squareup.picasso.Picasso

class AnimeSearchAdapter(_mSearchResults: MutableList<AnimeSearchModel>,  _mOnListListener: OnListListener): RecyclerView.Adapter<AnimeSearchAdapter.ViewHolder>(){

    private val mSearchResults = _mSearchResults
    private val mOnListListener = _mOnListListener

    inner class ViewHolder(itemView: View, _mOnListListener: OnListListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        val onListListener: OnListListener = _mOnListListener
        val animeTitle: TextView
        val animeSeason: TextView
        val animeMeanScore: TextView
        val animeCoverImage: ImageView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            animeTitle = itemView.findViewById(R.id.animeTitle)
            animeSeason = itemView.findViewById(R.id.animeSeason)
            animeMeanScore = itemView.findViewById(R.id.animeScore)
            animeCoverImage = itemView.findViewById(R.id.animeCoverImage)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onListListener.onClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.rv_search_anime, parent, false)
        // Return a new holder instance

        return ViewHolder(contactView, mOnListListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val anime = mSearchResults[position]

        val animeTitle = viewHolder.animeTitle
        val animeSeason = viewHolder.animeSeason
        val animeMeanScore = viewHolder.animeMeanScore
        val animeCoverImage = viewHolder.animeCoverImage

        Picasso.get().load(anime.coverImage).into(animeCoverImage)

        if(anime.title!!.length >= 30) {
            val titleString = anime.title?.substring(0,29) + "..."
            animeTitle.text = titleString
        } else {
            animeTitle.text = anime.title
        }

        animeMeanScore.text = ("Score: " + anime.meanScore)

        if (anime.season != null && anime.seasonYear != null){
            animeSeason.text = (anime.season + " " + anime.seasonYear)
        } else if (anime.seasonYear != null) {
            animeSeason.text = anime.seasonYear.toString()
        } else {
            animeSeason.text = ""
        }

        if(position %2 == 1)
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"))
        }
    }

    override fun getItemCount(): Int {
        return mSearchResults.count()
    }

    interface OnListListener{
        fun onClick(position: Int)
    }
}