package com.example.alfateam.presenter.registration


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.alfateam.R
import com.example.alfateam.databinding.FragmentRegistrationBinding
import com.example.alfateam.entity.Hero
import com.example.alfateam.presenter.main.State
import com.example.alfateam.presenter.registration.adapter.HeroAdapter


import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject


@AndroidEntryPoint
class Registration : Fragment() {

    companion object {
        fun newInstance() = Registration()
        sealed class Action{
            object NoChooseHero:Action()
            object ChooseHero:Action()
        }
    }

    @Inject
    lateinit var refistrationFactory:RegistrationViewModelFactory

     val adapterHero=HeroAdapter {hero,position -> onClickHero(hero,position) }
    private val viewModel: RegistrationViewModel by viewModels{refistrationFactory}

    private var scrollRcPerson=true
    private var action: Action =Action.NoChooseHero

    private var hero:Hero?=null
    lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRegistrationBinding.inflate(inflater)

        binding.rcPerson.adapter=adapterHero

        viewModel.heroStatus.observe(viewLifecycleOwner) {
                adapterHero.submitList(it)
        }

        binding.btnReg.setOnClickListener {
            viewModel.checkInput(binding.loginLayoutEdit.text.toString(),
                binding.emailLayoutEdit.text.toString(),
                binding.passwordLayoutEdit.text.toString(),
                hero
            )
        }


        binding.rcPerson.layoutManager= object : LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false){ override fun canScrollHorizontally(): Boolean { return scrollRcPerson } }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
           viewModel.inputState.collect{
               when(it){
                   is State.Error -> {
                       binding.progressBar.visibility=View.GONE
                       if(it.message["Login"]!=null) {
                           binding.loginLayout.error = it.message["Login"]
                       }else{
                           binding.loginLayout.error =null
                       }
                       if(it.message["Email"]!=null) {
                           binding.emailLayout.error = it.message["Email"]
                       }else{
                           binding.emailLayout.error =null
                       }
                       if(it.message["Password"]!=null) {
                           binding.passwordLayout.error = it.message["Password"]
                       }else{
                           binding.passwordLayout.error =null
                       }
                       if(it.message["Hero"]!=null){
                           binding.errorHeroTxt.visibility=View.VISIBLE
                       }else{
                           binding.errorHeroTxt.visibility=View.INVISIBLE
                       }

                   }
                   State.Loading -> {
                       binding.progressBar.visibility=View.VISIBLE
                   }
                   State.Success -> {
                       binding.progressBar.visibility=View.GONE
                       binding.loginLayout.error =null
                       binding.emailLayout.error =null
                       binding.passwordLayout.error =null
                       binding.errorHeroTxt.visibility=View.INVISIBLE
                   }
               }
           }
       }

        binding.loginText.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_mainFragment)
            findNavController().popBackStack()
        }

        return binding.root
    }


    fun onClickHero(item:Hero,position :Int){
        hero=item

        if (action==Action.NoChooseHero){
            binding.rcPerson.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    scrollRcPerson = false
                }
            })
        }
        if (action==Action.ChooseHero){
            scrollRcPerson=true
            binding.rcPerson.clearOnScrollListeners()
        }

        if (action==Action.NoChooseHero){
            binding.rcPerson.post {
                binding.rcPerson.scrollToPosition(position)
            }
            action=Action.ChooseHero
        }else{
            action=Action.NoChooseHero
        }
    }
}