package com.example.restaurant.presentration.searchHistory


import android.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.databinding.ActivitySearchHistoryBinding
import com.example.restaurant.databinding.ShopSearchFragmentBinding
import com.example.restaurant.presentration.ShopSearch.ShopsearchFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SearchHistoryActivity : AppCompatActivity(),RecyclerViewClickListener {
    private var searchHistoryList = ArrayList<String?>()
    private lateinit var binding: ActivitySearchHistoryBinding
    private lateinit var adapter: SearchHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchHistoryList = getArrayList("search_history_list")
        Collections.reverse(searchHistoryList)
        setupRecyclerView()
    }

    fun getArrayList(key: String?): ArrayList<String?> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json: String? = prefs.getString(key, "[]")
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }

    private fun setupRecyclerView() {
        binding.progressBar.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.GONE
        adapter = SearchHistoryAdapter(this,searchHistoryList,this)
        binding.shopRv.layoutManager = LinearLayoutManager(this)
        binding.shopRv.adapter = adapter

    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun recyclerViewListClicked(v: View?, position: String?) {
        val intent = Intent()
        intent.putExtra("data",position)
        Log.e("data: ",position!!)
        setResult(RESULT_OK,intent);
        finish();
       
    }


}


