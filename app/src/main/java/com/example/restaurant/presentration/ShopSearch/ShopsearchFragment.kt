package com.example.restaurant.presentration.ShopSearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurant.R
import com.example.restaurant.data.entities.Shop
import com.example.restaurant.databinding.ShopsFragmentBinding
import com.example.restaurant.usecase.Constant
import com.example.restaurant.usecase.Resource
import com.example.restaurant.usecase.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopsearchFragment : Fragment(), ShopsAdapter.ShopItemListener {

    private var binding: ShopsFragmentBinding  by autoCleared()
    private val viewModel: ShopsearchViewModel by viewModels()
    private lateinit var adapter: ShopsAdapter
    private  var list = ArrayList<Shop>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShopsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        binding.searchNameEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
        getView()?.setFocusableInTouchMode(true)
        getView()?.requestFocus()
        getView()?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (keyCode == KeyEvent.KEYCODE_BACK) {
                    true
                } else false
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = ShopsAdapter(requireContext(),list,this)
        binding.shopRv.layoutManager = LinearLayoutManager(requireContext())
        binding.shopRv.adapter = adapter

    }

    private fun setupObservers() {
        viewModel.shops.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.shimmerViewContainer.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()){
                        list = it.data as ArrayList<Shop>
                        adapter.setItems(it.data)

                    }
                }
                Resource.Status.ERROR ->{
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.shimmerViewContainer.visibility = View.VISIBLE
                }

            }
        })
    }

    override fun onClickedShops(shopId: String) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment,
            bundleOf("id" to shopId)
        )
        Constant.gotoDetailsPage = 2
    }

   override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

  override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }




}
