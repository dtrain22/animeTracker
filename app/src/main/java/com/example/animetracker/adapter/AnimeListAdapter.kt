package com.example.animetracker.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animetracker.R
import com.example.animetracker.data.models.AnimeListModel
import com.example.animetracker.type.MediaListStatus

class AnimeListAdapter(private val mAnimeList: List<AnimeListModel>) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var animeTitle: TextView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            animeTitle= itemView.findViewById(R.id.anime_name)
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_anime_list, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: AnimeListAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val anime: AnimeListModel = mAnimeList[position]
        // Set item views based on your views and data model
        val textView = viewHolder.animeTitle
        textView.text = anime.title
        if(anime.status == MediaListStatus.COMPLETED){
            textView.setTextColor(Color.parseColor("#00FF00"))
        } else if(anime.status == MediaListStatus.CURRENT){
            textView.setTextColor(Color.parseColor("#0000EE"))
        } else if(anime.status == MediaListStatus.DROPPED){
            textView.setTextColor(Color.parseColor("#FF0000"))
        } else if(anime.status == MediaListStatus.PAUSED){
            textView.setTextColor(Color.parseColor("#008080"))
        } else if(anime.status == MediaListStatus.PLANNING){
            textView.setTextColor(Color.parseColor("#696969"))
        }
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mAnimeList.size
    }
}