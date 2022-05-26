package com.example.restaurant.presentration

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.databinding.ItemShopBinding
import com.example.restaurant.presentration.restaurants.RestaurantDetailScreenActivity

class ShopListAdapter(): RecyclerView.Adapter<ShopViewHolder>() {
    private lateinit var itemList:ArrayList<Shop>
    private lateinit var context: Context

    constructor(context: Context, items: ArrayList<Shop>) : this() {
        this.itemList = items
        this.context = context

    }
    fun setItems(items: ArrayList<Shop>) {
        this.itemList?.clear()
        this.itemList?.addAll(items)
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, RestaurantDetailScreenActivity::class.java)
            intent.putExtra("shop", itemList[position])
            context.startActivity(intent)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding: ItemShopBinding =
            ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }


}
class ShopViewHolder(private val itemBinding: ItemShopBinding) :
    RecyclerView.ViewHolder(itemBinding.root){

    private lateinit var shop: Shop

    @SuppressLint("SetTextI18n")
    fun bind(item: Shop) {
        this.shop = item
        itemBinding.name.text = item.name
        itemBinding.access.text = item.access
        itemBinding.address.text = item.address
        Glide.with(itemBinding.root)
            .load(item.photo?.mobile?.longSize)
            .into(itemBinding.image)
       // itemBinding.image.setImageURI(Uri.parse(item.photo?.mobile?.longSize))
        Log.e("ImageURL: ", item.photo?.mobile?.longSize!!)
    }


}