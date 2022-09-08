package com.example.alfateam.data

import com.example.alfateam.data.api.RetrofitServiceDollar
import com.example.alfateam.entity.dollar.Dollar
import javax.inject.Inject

class RepositoryDollar @Inject constructor() {

   suspend fun getDollar():Dollar{
       return RetrofitServiceDollar.searchDollarApi.getDollar()
   }

}