package com.example.restaurant.cleanArchitecture.data.repository



import androidx.lifecycle.MutableLiveData
import com.example.restaurant.cleanArchitecture.data.model.Root
import com.example.restaurant.cleanArchitecture.data.remote.APIService
import com.example.restaurant.cleanArchitecture.data.remote.RetroClass
import io.reactivex.rxjava3.core.Observable


class ShopRepository {

    lateinit var apiService: APIService
    private var _id = MutableLiveData<String>()
    var shopResponseObservableWithID: Observable<Root>
        get():Observable<Root> {
            apiService = RetroClass.getAPIService()
            shopResponseObservableWithID = apiService.
            getShop("f5c06c8896e2d2d4", _id.value!!,"json")
            return shopResponseObservableWithID
        }
        set(value) {}
    var shopResponseObservable: Observable<Root>
        get():Observable<Root> {
            apiService = RetroClass.getAPIService()
            shopResponseObservable = apiService.
            getAllShops("f5c06c8896e2d2d4","Z011","json")
            return shopResponseObservable
        }
        set(value) {}

    constructor(_id: MutableLiveData<String>) {
        this._id = _id
    }
}