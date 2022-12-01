package com.example.alfateam.presenter.main


import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.alfateam.R
import com.example.alfateam.databinding.FragmentMainBinding
import com.example.alfateam.entity.Constance
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainFactory:MainViewModelFactory
    private val viewModel: MainViewModel by viewModels{mainFactory}
    lateinit var binding: FragmentMainBinding

    private val auth= FirebaseAuth.getInstance()
    private var email:String=""
    private var password:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentMainBinding.inflate(inflater)


     if(auth.currentUser!=null){
         val navOptions: NavOptions =NavOptions.Builder()
             .setPopUpTo(R.id.mainFragment, true)
             .build()
        findNavController().navigate(R.id.action_mainFragment_to_menuFragment,null,navOptions=navOptions)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.checkInput(binding.editEmail.text.toString(),binding.editPassword.text.toString(),binding.checkSidor.isChecked)
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.inputState.collect{
                when(it){
                    is State.Error -> {
                        if(it.message["Email"]!=null) {
                            binding.editEmail.error = it.message["Email"]
                        }else{
                            binding.editEmail.error =null
                        }
                        if(it.message["Password"]!=null) {
                            binding.editPassword.error = it.message["Password"]
                        }else{
                            binding.editPassword.error=null
                        }
                        if(it.message["Sidor"]!=null){
                            binding.errorSidor.visibility=View.VISIBLE
                        }else{
                            binding.errorSidor.visibility=View.GONE
                        }
                        if(it.message["Registration"]!=null){
                            binding.errorPasswordEmail.visibility=View.VISIBLE
                            binding.editPassword.text?.clear()
                        }else{
                            binding.errorPasswordEmail.visibility=View.GONE
                        }
                    }
                    State.Loading -> {
                    }
                    State.Success -> {
                        binding.editEmail.error =null
                        binding.editPassword.error=null
                        binding.errorSidor.visibility=View.GONE
                        binding.errorPasswordEmail.visibility=View.GONE

                            val navOptions: NavOptions = NavOptions.Builder()
                                .setPopUpTo(R.id.mainFragment, true)
                                .build()
                            findNavController().navigate(
                                R.id.action_mainFragment_to_menuFragment,
                                null,
                                navOptions = navOptions
                            )

                    }
                    State.Start -> {
                        binding.editEmail.text?.clear()
                        binding.editEmail.error =null
                        binding.editPassword.text?.clear()
                        binding.editPassword.error=null
                        binding.errorSidor.visibility=View.GONE
                        binding.errorPasswordEmail.visibility=View.GONE
                    }
                }
            }
        }

        binding.regTextGo.setOnClickListener {
            viewModel.refreshState()
            findNavController().navigate(R.id.action_mainFragment_to_registration)
        }
        return binding.root
    }


}