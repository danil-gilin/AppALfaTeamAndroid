package com.example.alfateam.presenter.sad

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.alfateam.R
import com.example.alfateam.databinding.FragmentSadBinding
import com.example.alfateam.entity.SadBoy
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SadFragment : Fragment() {

    companion object {
        fun newInstance() = SadFragment()
    }

    @Inject
    lateinit var sadFragmentFactory: SadViewModelFactory

    private val viewModel: SadViewModel by viewModels { sadFragmentFactory }

    lateinit var binding: FragmentSadBinding
    private val leftToRight = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,-300f,0f)
    private val righttoLeft =PropertyValuesHolder.ofFloat(View.TRANSLATION_X,+300f,0f)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSadBinding.inflate(inflater)
        animButton()

        binding.backButtonSad.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMinus.setOnClickListener {
            viewModel.minusMoral(SadBoy(binding.countSad.text.toString().toInt(),"Он любит вику"))
        }
        binding.btnPlus.setOnClickListener {
            viewModel.plusMoral(SadBoy(binding.countSad.text.toString().toInt(),"Он выйграл в казино"))
        }

        viewModel.moral.observe(viewLifecycleOwner){sadboy->
            binding.countSad.alpha=0.0f
            if(sadboy.moral!=0) {
                binding.countSad.animate().apply {
                    duration = 500
                    alphaBy(1.0F)
                    start()
                }
                binding.btnPlus.isClickable=true
                binding.btnMinus.isClickable=true
            }else{
                binding.btnPlus.isClickable=false
                binding.btnMinus.isClickable=false
            }
            if(sadboy.description!=binding.txtWhySad.text){
                binding.txtWhySad.alpha=0.0f
                binding.txtWhySad.animate().apply {
                    duration = 500
                    alphaBy(1.0F)
                    start()
                }
            }
            if(sadboy.img!=null) {
                binding.lotieSad.setAnimation(sadboy.img!!)
                binding.lotieSad.playAnimation()
            }
            binding.countSad.text=sadboy.moral.toString()
            binding.txtWhySad.text=sadboy.description
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect{state->
                when(state){
                    is StateSad.Error ->{
                        Snackbar.make(requireView(),state.message,Snackbar.LENGTH_LONG).show()
                        binding.btnPlus.isClickable=true
                        if (state.flagLowMoral){
                            binding.btnMinus.isEnabled=false
                        }
                    }
                    StateSad.Loading -> {
                        binding.btnPlus.isClickable=false
                        binding.btnMinus.isClickable=false
                    }
                    StateSad.Success ->{
                        binding.btnMinus.isEnabled=true
                        binding.btnPlus.isClickable=true
                        binding.btnMinus.isClickable=true
                    }
                   is StateSad.Start->{
                          binding.btnPlus.isEnabled = state.flagPermission
                          binding.btnMinus.isEnabled =state.flagPermission
                   }
                }
            }
        }

        return binding.root
    }

    private fun animButton() {
        ObjectAnimator.ofPropertyValuesHolder( binding.btnPlus,righttoLeft).apply{
            duration=1500
            interpolator= AccelerateInterpolator()
            start()
        }
        ObjectAnimator.ofPropertyValuesHolder( binding.btnMinus,leftToRight).apply{
            duration=1500
            interpolator= AccelerateInterpolator()
            start()
        }
    }
}