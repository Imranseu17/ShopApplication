package com.example.restaurant.cleanArchitecture.presentration.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.restaurant.cleanArchitecture.presentration.adapter.ShopListAdapter
import com.example.restaurant.cleanArchitecture.presentration.viewmodel.ShopViewModel
import com.example.restaurant.databinding.FragmentShopListCleanBinding
import com.example.restaurant.usecase.autoCleared
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class ShopListFragment : Fragment() {
    private var binding: FragmentShopListCleanBinding by autoCleared()
    lateinit var viewModel: ShopViewModel
    private lateinit var adapter: ShopListAdapter
    private var _id = MutableLiveData<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentShopListCleanBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ShopViewModel(_id)
        binding.setUserPresenter.setOnClickListener {

            viewModel.responseObservable.
            observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe()
        }
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


}