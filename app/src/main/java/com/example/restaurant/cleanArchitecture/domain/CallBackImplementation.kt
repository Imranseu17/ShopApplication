package com.example.restaurant.cleanArchitecture.domain

import androidx.lifecycle.MutableLiveData
import com.example.restaurant.cleanArchitecture.data.model.Root
import com.example.restaurant.cleanArchitecture.data.repository.ShopRepository
import io.reactivex.rxjava3.core.Observable

class CallBackImplementation : CallBackInterface {
    lateinit var repository:ShopRepository
    lateinit var responseObservable: Observable<Root>
    lateinit var responseObservableWithID: Observable<Root>
    private var _id = MutableLiveData<String>()

    constructor(_id: MutableLiveData<String>) {
        this._id = _id
        repository = ShopRepository(_id)
    }


    override fun callBackResponseList(): Observable<Root> {
        responseObservable = repository.shopResponseObservable
        return responseObservable
    }

    override fun callBackResponse(): Observable<Root> {
        responseObservableWithID = repository.shopResponseObservableWithID
        return responseObservableWithID
    }
}