package com.example.restaurant.presentration.ShopSearch

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.databinding.ItemShopBinding
import java.util.*
import kotlin.collections.ArrayList

class ShopsAdapter() :
    RecyclerView.Adapter<ShopViewHolder>(),Filterable
{

    private lateinit var itemList:ArrayList<Shop>
    private lateinit var itemListFilter: ArrayList<Shop>
    private lateinit var context:Context
    private lateinit var listener:ShopItemListener

    constructor(context: Context,items: ArrayList<Shop>,listener: ShopItemListener) : this() {
        this.itemListFilter = items
        this.itemList = items
        this.listener = listener
        this.context = context

    }


    interface ShopItemListener {
        fun onClickedShops(shopId: String)
    }


    fun setItems(items: ArrayList<Shop>) {
        this.itemList?.clear()
        this.itemList?.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding: ItemShopBinding =
            ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = itemListFilter.size

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) =
        holder.bind(itemListFilter[position])

    @OptIn(ExperimentalStdlibApi::class)
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                       itemListFilter = itemList
                } else {
                    val filteredList: ArrayList<Shop> = ArrayList()
                    for (row in itemList) {

                        if (row.name!!.toLowerCase()
                                .contains(charString.lowercase(Locale.getDefault()))
                            || row.address
                                !!.contains(charSequence)
                        ) {
                            filteredList.add(row)
                        }
                    }
                    itemListFilter = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = itemListFilter
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                itemListFilter = filterResults.values as ArrayList<Shop>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }
}

class ShopViewHolder(private val itemBinding: ItemShopBinding,
                     private val listener: ShopsAdapter.ShopItemListener) :
    RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var shop: Shop

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Shop) {
        this.shop = item
        itemBinding.name.text = item.name
        itemBinding.access.text = item.access
        itemBinding.address.text = item.address
        Glide.with(itemBinding.root)
            .load(item.photo?.mobile?.longSize)
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
       listener.onClickedShops(shop.id)
    }


}

