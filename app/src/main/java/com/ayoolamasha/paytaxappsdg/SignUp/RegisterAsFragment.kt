package com.ayoolamasha.paytaxappsdg.SignUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.ayoolamasha.paytaxappsdg.R

class RegisterAsFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterAsFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        view.findViewById<Button>(R.id.nextButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_registerAsFragment_to_signUpFragment)
        }

        view.findViewById<Button>(R.id.backToLogin).setOnClickListener {
            view.findNavController().navigate(R.id.action_registerAsFragment_to_loginFragment)
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}