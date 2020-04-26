package com.example.animetracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animetracker.R
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.type.MediaListStatus
import com.squareup.picasso.Picasso

class AnimeListAdapter(_mAnimeList: List<AnimeListModel>, _mOnListListener: OnListListener) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>()
{
    private val mAnimeList = _mAnimeList
    private val mOnListListener = _mOnListListener

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View, _onListListener: OnListListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        val onListListener: OnListListener = _onListListener
        val animeTitle: TextView
        val animeStatus: TextView
        val animeProgress: TextView
        val animeScore: TextView
        val animeCoverImage: ImageView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            animeTitle = itemView.findViewById(R.id.animeTitle)
            animeStatus = itemView.findViewById(R.id.animeStatus)
            animeProgress = itemView.findViewById(R.id.animeProgress)
            animeScore = itemView.findViewById(R.id.animeScore)
            animeCoverImage = itemView.findViewById(R.id.animeCoverImage)

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onListListener.onClick(adapterPosition)
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.rv_anime_list, parent, false)
        // Return a new holder instance

        return ViewHolder(contactView, mOnListListener)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: AnimeListAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val anime: AnimeListModel = mAnimeList[position]
        // Set item views based on your views and data model
        val animeTitle = viewHolder.animeTitle
        val animeStatus = viewHolder.animeStatus
        val animeProgress = viewHolder.animeProgress
        val animeScore = viewHolder.animeScore
        val animeCoverImage = viewHolder.animeCoverImage


        Picasso.get().load(anime.coverImage).into(animeCoverImage)

        if(anime.title!!.length >= 30) {
            val titleString = anime.title?.substring(0,29) + "..."
            animeTitle.text = titleString
        } else {
            animeTitle.text = anime.title
        }

        animeScore.text = ("Score: " + anime.score.toString())

        if(anime.totalEpisodes == null) {
            animeProgress.text = ("Progress: " + anime.progress.toString() + "/?")
        } else {
            animeProgress.text = ("Progress: " + anime.progress.toString() + "/" + anime.totalEpisodes.toString())
        }

        animeStatus.text = anime.status.toString()
        animeStatus.setTextColor(Color.parseColor(getColorString(anime)))

        if(position %2 == 1)
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"))
        }
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mAnimeList.size
    }

    private fun getColorString(anime: AnimeListModel): String {
        return when(anime.status){
            MediaListStatus.COMPLETED -> "#00FF00"
            MediaListStatus.CURRENT -> "#0000EE"
            MediaListStatus.REPEATING -> "#0000EE"
            MediaListStatus.DROPPED -> "#FF0000"
            MediaListStatus.PAUSED -> "#008080"
            else -> "#696969"
        }
    }

    interface OnListListener{
        fun onClick(position: Int)
    }
}