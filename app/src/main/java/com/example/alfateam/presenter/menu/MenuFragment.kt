package com.example.alfateam.presenter.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.alfateam.R
import com.example.alfateam.databinding.FragmentMenuBinding
import com.example.alfateam.entity.MenuApp
import com.example.alfateam.presenter.menu.rc_adapter.MenuAppAdapter
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    @Inject
   lateinit var menuFactory:MenuViewModelFactory

    private val auth= FirebaseAuth.getInstance()


    private val adapter=MenuAppAdapter{item->onClickApp(item)}


    private val viewModel: MenuViewModel by viewModels {menuFactory  }
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMenuBinding.inflate(inflater)

        viewModel.getUserInfo(auth.currentUser?.uid.toString())

        binding.rcApp.adapter=adapter
        adapter.submitList(listOf(MenuApp("dollar",R.raw.sad_lottie,R.id.action_menuFragment_to_dollarFragment),
            MenuApp("dollar",R.raw.dollar_lottie,R.id.action_menuFragment_to_dollarFragment),
            MenuApp("dollar",R.raw.sad_lottie,R.id.action_menuFragment_to_dollarFragment)))
binding.rcApp.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
           viewModel.state.collect{
               when(it){
                   is StateMenu.Error -> {
                       binding.progressMenu.visibility=View.GONE
                       binding.heroImgMenu.visibility=View.VISIBLE
                       binding.loginTxt.visibility=View.VISIBLE
                       binding.raiting.visibility=View.VISIBLE
                       binding.ratingTxt.visibility=View.VISIBLE
                   }
                   StateMenu.Loading -> {
                       binding.progressMenu.visibility=View.VISIBLE
                       binding.heroImgMenu.visibility=View.INVISIBLE
                       binding.loginTxt.visibility=View.INVISIBLE
                       binding.raiting.visibility=View.INVISIBLE
                       binding.ratingTxt.visibility=View.INVISIBLE
                   }
                   is StateMenu.Success ->{
                       binding.heroImgMenu.visibility=View.VISIBLE
                       binding.loginTxt.visibility=View.VISIBLE
                       binding.raiting.visibility=View.VISIBLE
                       binding.ratingTxt.visibility=View.VISIBLE
                       binding.progressMenu.visibility=View.GONE
                       binding.loginTxt.text=it.user.login
                       binding.raiting.progress=it.user.hero.rating
                       Glide.with(binding.heroImgMenu.context)
                           .load(it.user.hero.img_url).centerInside()
                           .into(binding.heroImgMenu)
                   }
               }
           }
        }

        return binding.root
    }

    private fun onClickApp(item:MenuApp){
        findNavController().navigate(item.nav_id)
    }



}