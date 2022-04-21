package com.example.restaurant.presentration.searchHistory

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurant.databinding.SearchHistoryLayoutBinding

class SearchHistoryAdapter () :
    RecyclerView.Adapter<SearchHistoryAdapter.HistoryAdapter>() {

    private lateinit var itemList:ArrayList<String?>
    private lateinit var context:Context
    private lateinit var listener: RecyclerViewClickListener

    constructor(context: Context, items: ArrayList<String?>, listener: RecyclerViewClickListener) : this() {
        this.itemList = items
        this.listener = listener
        this.context = context

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter {

        val binding: SearchHistoryLayoutBinding =
            SearchHistoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryAdapter(binding, listener)
    }

    override fun onBindViewHolder(holder: HistoryAdapter, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class HistoryAdapter(private val itemBinding: SearchHistoryLayoutBinding,
                     private  val  listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        init {
            itemBinding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: String?) {
            itemBinding.listData.text = item
        }


        override fun onClick(v: View?) {
                listener.recyclerViewListClicked(v,itemBinding.listData.text.toString())
        }
    }

}