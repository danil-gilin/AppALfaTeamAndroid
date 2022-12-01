package com.example.alfateam.data

import android.util.Log
import com.example.alfateam.R
import com.example.alfateam.entity.Constance
import com.example.alfateam.entity.Hero
import com.example.alfateam.entity.SadBoy
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SadRepository @Inject constructor() {
   private val database = Firebase.database(Constance.FIREBASE_REALTIME)
   private val myRef = database.getReference("SADBOY")
    private  val dbFirestoreUser = FirebaseFirestore.getInstance().collection("User")


    fun getMoral():Flow<SadBoy>{
        var sadBoy = MutableStateFlow<SadBoy>(SadBoy(0,""))
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val moral =(snapshot.child("moral").value as Long).toString().toInt()
                val tempSadBoy=SadBoy(
                    moral,
                    snapshot.child("description").value as String,
                    (snapshot.child("img").value as Long).toString().toInt()
                )
                  sadBoy.value=tempSadBoy
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return sadBoy
    }

  suspend  fun checkUserPermission(uid:String,time:Long):Boolean{
       val lastTime= dbFirestoreUser.document(uid).get().await().getLong("Time")

      if(time-Constance.ONE_DAY>lastTime!!){
          Log.d("Sidor","true")
          return true
      }else{
          Log.d("Sidor","false")
          return false
      }
    }

    suspend fun minusMoral(sadBoy: SadBoy,uid:String,time:Long){
        dbFirestoreUser.document(uid).update("Time",time)
       when(sadBoy.moral){
          in 0..300->{
              sadBoy.description=SAD_TEXT
              sadBoy.img=SAD_IMG
          }
           in 300..600->{
               sadBoy.description=NORMAL_TEXT
               sadBoy.img=NORMAL_IMG
           }
           else->{
               sadBoy.description=LUCKY_TEXT
               sadBoy.img=LUCKY_IMG
           }
       }
        sadBoy.description
       myRef.setValue(sadBoy).await()
    }

    suspend fun plusMoral(sadBoy: SadBoy,uid:String,time:Long){
        dbFirestoreUser.document(uid).update("Time",time)
        when(sadBoy.moral){
            in 0..300->{
                sadBoy.description=SAD_TEXT
                sadBoy.img=SAD_IMG
            }
            in 300..600->{
                sadBoy.description=NORMAL_TEXT
                sadBoy.img=NORMAL_IMG
            }
            else->{
                sadBoy.description=LUCKY_TEXT
                sadBoy.img=LUCKY_IMG
            }
        }
        myRef.setValue(sadBoy).await()
    }
    companion object{
        const val SAD_TEXT="Ему очень плохо из-за Вики"
        const val SAD_IMG= R.raw.sad_sidor
        const val NORMAL_TEXT="Он выйграл катку в доте"
        const val NORMAL_IMG=R.raw.normal_sidor
        const val LUCKY_TEXT="Он Выиграл на рубликсе"
        const val LUCKY_IMG=R.raw.lucky_sidor
    }
}