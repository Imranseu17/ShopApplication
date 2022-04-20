package com.example.restaurant.presentration.searchHistory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.R
import java.util.ArrayList

class SearchHistoryAdapter (val context: Context, val list: ArrayList<String?>) :
    RecyclerView.Adapter<SearchHistoryAdapter.HistoryAdapter>() {



    inner class HistoryAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val list_data = itemView.findViewById<TextView>(R.id.list_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter {

        lateinit var view: View

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_layout, parent, false)


        return HistoryAdapter(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter, position: Int) {
        holder.list_data.setText(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}