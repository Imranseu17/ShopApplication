package com.example.restaurant.cleanArchitecture.presentration.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurant.cleanArchitecture.data.model.Root
import com.example.restaurant.cleanArchitecture.domain.CallBackImplementation
import io.reactivex.rxjava3.core.Observable

class ShopViewModel :ViewModel {
    lateinit var callBackImplementation:CallBackImplementation
    lateinit var responseObservable:  Observable<Root>
    lateinit var responseObservableWithID:  Observable<Root>
    private var _id = MutableLiveData<String>()

    constructor(_id: MutableLiveData<String>) : super() {
        this._id = _id
        callBackImplementation = CallBackImplementation(_id)
        responseObservable = callBackImplementation.responseObservable
        responseObservableWithID = callBackImplementation.responseObservableWithID
    }
}