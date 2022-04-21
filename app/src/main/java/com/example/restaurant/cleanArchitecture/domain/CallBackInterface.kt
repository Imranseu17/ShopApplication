package com.example.restaurant.cleanArchitecture.domain

import com.example.restaurant.cleanArchitecture.data.model.Root
import io.reactivex.rxjava3.core.Observable

interface CallBackInterface {
   fun callBackResponseList(): Observable<Root>
   fun callBackResponse(): Observable<Root>
}