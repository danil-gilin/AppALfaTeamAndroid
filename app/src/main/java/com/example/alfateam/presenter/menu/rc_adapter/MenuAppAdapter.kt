package com.example.alfateam.presenter.menu.rc_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alfateam.R
import com.example.alfateam.databinding.MenuappItemBinding

import com.example.alfateam.entity.Hero

import com.example.alfateam.entity.MenuApp


class MenuAppAdapter (private val onClick :(MenuApp)->Unit):ListAdapter<MenuApp,MenuAppViewHolder>(MenuDiffutilCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAppViewHolder {
        return MenuAppViewHolder(MenuappItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MenuAppViewHolder, position: Int) {
        val item=getItem(position)
        with(holder.binding){
            appName.text=item.name
            lotieAnimApp.setAnimation(item.img_url)
        }
        holder.binding.root.setOnClickListener {
            onClick(item)
        }



    }

}

class MenuAppViewHolder(val binding: MenuappItemBinding):RecyclerView.ViewHolder(binding.root)

class MenuDiffutilCallBack: DiffUtil.ItemCallback<MenuApp>(){
    override fun areItemsTheSame(oldItem: MenuApp, newItem: MenuApp): Boolean {
        return oldItem.nav_id==newItem.nav_id
    }

    override fun areContentsTheSame(oldItem: MenuApp, newItem: MenuApp): Boolean {
       return oldItem==newItem
    }

}