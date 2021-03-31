package com.ayoolamasha.paytaxappsdg.Login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.R
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpErrorPojo
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpResponse
import com.ayoolamasha.paytaxappsdg.Utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }


    private lateinit var viewModel: LoginViewModel
    private lateinit var taxId: TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var login:Button

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application))
            .get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        init(view)

        view.findViewById<Button>(R.id.goToSignUp).setOnClickListener{

            view.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

//        view.findViewById<Button>(R.id.login).setOnClickListener {
//            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }

        login.setOnClickListener {
            loginRequest()
        }

        viewModel.mLiveLoginResponse.observe(requireActivity(), { type ->
            run {
                coroutineScope.launch {
                    when (type) {
                        is ApiResult.Success -> {
                            val success = type.response as LoginRequestResponse
                            //Timber.tag("success").d(success.loginResponseData.taxPayerId)
                            view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        is ApiResult.NetworkError -> {
                            Snackbar.make(
                                view,
                                "Check your Network Connection",
                                Snackbar.LENGTH_SHORT
                            ).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                        }
                        is ApiResult.Failure -> {
                            val error: SignUpErrorPojo = type as SignUpErrorPojo
                            Timber.tag("Failure").d(error.message)
                            Snackbar.make(
                                view,
                                error.message!!,
                                Snackbar.LENGTH_SHORT
                            ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
                        }
                        is ApiResult.Exception -> {
                            Timber.tag("Exception").d(type.t)
                            type.t.message?.let {
                                Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
                                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
                            }
                        }
                        else -> ApiResult.NetworkError(true)
                    }
                }
            }

        })

        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun init(view:View){
        taxId = view.findViewById(R.id.inputTaxId)
        password = view.findViewById(R.id.inputPassword)
        login = view.findViewById(R.id.login)
    }

    private fun loginRequest(){
        val userTaxId = taxId.text.toString().trim()
        val userPassword = password.text.toString().trim()

        if (userTaxId.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(activity, "Fill all Fields" , Toast.LENGTH_SHORT).show()
        }else{
            viewModel.userLogin(userTaxId, userPassword)
        }
    }

}