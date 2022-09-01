package com.example.alfateam.presenter.registration.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alfateam.R
import com.example.alfateam.databinding.HeroBinding

import com.example.alfateam.entity.Hero


class HeroAdapter(private val onClick :(Hero,Int)->Unit):ListAdapter<Hero,HeroViewHolder>(DiffutilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(HeroBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HeroViewHolder, @SuppressLint("RecyclerView") position: Int) {
       val item=getItem(position)
        Log.d("FDB","go")
        with(holder.binding){
            if (item.img_url!="") {
                Log.d("FDB",item.img_url.toString())
                Glide.with(heroImg.context).load(item.img_url).centerInside().into(heroImg)
            }else{
                Log.d("FDB",item.img_url.toString())
                Glide.with(heroImg.context).load(item.img_url).centerInside().into(heroImg)
            }
            heroName.text=item.name
            txtDescription.text=item.description
        }

            holder.binding.root.setTransitionListener(object : MotionLayout.TransitionListener{
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {

                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if( currentId== R.id.start){
                       val paramsMotion=holder.binding.root.layoutParams as RecyclerView.LayoutParams
                       paramsMotion.width=RecyclerView.LayoutParams.WRAP_CONTENT
                       holder.binding.root.layoutParams=paramsMotion
                        Log.d("Tran","wrap")

                    }else {
                       val paramsMotion=holder.binding.root.layoutParams as RecyclerView.LayoutParams
                       paramsMotion.width=RecyclerView.LayoutParams.MATCH_PARENT
                       holder.binding.root.layoutParams=paramsMotion
                        Log.d("Tran","match")

                    }

                    holder.binding.root.post {
                        onClick(item,position)
                    }

                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }

            })


    }

}


class HeroViewHolder(val binding: HeroBinding):RecyclerView.ViewHolder(binding.root)

class DiffutilCallBack:DiffUtil.ItemCallback<Hero>() {
    override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
       return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
       return  oldItem==newItem
    }


}