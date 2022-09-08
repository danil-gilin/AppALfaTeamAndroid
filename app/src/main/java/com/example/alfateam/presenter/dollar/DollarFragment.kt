package com.example.alfateam.presenter.dollar

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.alfateam.R
import com.example.alfateam.databinding.FragmentDollarBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class DollarFragment : Fragment() {

    companion object {
        fun newInstance() = DollarFragment()
    }

    @Inject
    lateinit var factoryDollar:DollarViewModelFactory

    private val viewModel: DollarViewModel by viewModels{factoryDollar}
    lateinit var binding:FragmentDollarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDollarBinding.inflate(inflater)
        viewModel.getDolar()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.stateDollar.collect{state->
                when(state){
                    is StateDollar.Error -> {
                     binding.progressDollar.visibility=View.GONE
                     binding.courseDollar.text=state.message
                     binding.courseDollar.visibility=View.VISIBLE
                        binding.olegInfo.visibility=View.VISIBLE
                    }
                    StateDollar.Loading -> {
                        binding.progressDollar.visibility=View.VISIBLE
                        binding.courseDollar.visibility=View.INVISIBLE
                        binding.olegInfo.visibility=View.INVISIBLE
                    }
                    is StateDollar.Succses -> {
                        binding.progressDollar.visibility=View.GONE
                        binding.olegInfo.visibility=View.VISIBLE
                        binding.courseDollar.visibility=View.VISIBLE
                        binding.lotieSnack.setAnimation(state.lottie)
                        binding.lotieSnack.playAnimation()
                        binding.olegRezult.text=getString(state.message)
                        binding.olegRezult.setTextColor(ContextCompat.getColor(requireContext(),state.color))
                        binding.courseDollar.text="${state.course} RUB"
                    }
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }



}