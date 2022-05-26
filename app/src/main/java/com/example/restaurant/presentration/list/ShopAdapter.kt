package com.example.restaurant.presentration.list

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.bumptech.glide.Glide
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.databinding.ItemShopBinding
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.util.*


class ShopAdapter(): RecyclerView.Adapter<ShopViewHolder>()
{

    private lateinit var itemList:ArrayList<Shop>
    private lateinit var context: Context
    private lateinit var listener:ShopListItemListener

    constructor(context: Context,items: ArrayList<Shop>,listener: ShopListItemListener) : this() {
    this.itemList = items
    this.listener = listener
    this.context = context

}


    interface ShopListItemListener {
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
        return ShopViewHolder(binding, listener = listener )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) =
        holder.bind(itemList[position])


}

class ShopViewHolder(private val itemBinding: ItemShopBinding,
                     private val listener: ShopAdapter.ShopListItemListener) :
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