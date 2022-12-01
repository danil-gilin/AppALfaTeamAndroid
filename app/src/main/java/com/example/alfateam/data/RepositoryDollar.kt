package com.example.alfateam.data

import android.util.Log
import com.example.alfateam.data.api.RetrofitServiceDollar
import com.example.alfateam.entity.Constance
import com.example.alfateam.entity.dollar_to_rub.DollarTimeQuery
import com.example.alfateam.entity.dollar_to_rub.DollarToRub
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryDollar @Inject constructor() {

    private val database = Firebase.database(Constance.FIREBASE_REALTIME)
    private val myRef = database.getReference("USD_TO_RUB")


   suspend fun getDollar(time:Long):Double{

      if(time-Constance.ONE_DAY> (myRef.get().await().child("time").value as Long)){
          return getRetrofit(time)
       }else{
          return getFirebase()
       }

   }

    suspend fun getRetrofit(time:Long):Double{

        val dollar=RetrofitServiceDollar.searchDollarApi.getDollar()
        myRef.setValue(DollarTimeQuery(dollar.result,time))

        return dollar.result
    }

    suspend fun getFirebase():Double{
     return ( myRef.get().await().child("course").value as Double)
    }

}