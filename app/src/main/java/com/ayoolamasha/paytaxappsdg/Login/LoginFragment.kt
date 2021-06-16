package com.ayoolamasha.paytaxappsdg.Login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.STATUS
import com.ayoolamasha.paytaxappsdg.BaseApplication
import com.ayoolamasha.paytaxappsdg.R
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpErrorPojo
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpResponse
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRepository
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var taxId: TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var login:CircularProgressButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        init(view)

        view.findViewById<Button>(R.id.goToSignUp).setOnClickListener{

            view.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }


        loginViewModel.mLoginRequestResponse.observe(requireActivity(), {
            run {
                lifecycleScope.launch {
                    when(it.STATUS){
                        STATUS.SUCCESS -> {
                            login.dispose()
                            it.data?.let {
                                response -> response as LoginRequestResponse
                                val fullName:String = response.loginResponseData.firstName + response.loginResponseData.lastName
                                val bundle = bundleOf(
                                "taxId" to response.loginResponseData.taxPayerId,
                                "accessToken" to response.accessToken,
                                "name" to fullName,
                                "userEmail" to response.loginResponseData.email
                            )
                            bundle.putString("userTaxId", response.loginResponseData.taxPayerId)
                            //bundle.putString("userAccessToken", response.accessToken)
                            bundle.putString("userName", fullName)


                                val userDataPojo = UserDataPojo(null, response.accessToken,
                                    response.loginResponseData._id, response.loginResponseData.firstName,
                                    response.loginResponseData.lastName, response.loginResponseData.email,
                                    response.loginResponseData.phone, response.loginResponseData.password, response.loginResponseData.taxPayerId)
                                loginViewModel.insertUserDataViewModel(userDataPojo)


                                view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)

                                Snackbar.make(view, "Login Successful", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()

                            }
                        }
                        STATUS.LOADING -> {

                            Snackbar.make(view, "Error Logging", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                        }
                        STATUS.ERROR-> {
                            //Handle Error
                            Snackbar.make(view, "Invalid Credentials", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                        }
                        else ->  Snackbar.make(view, "Check Your Network", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                    }
                }
            }
        })


        login.setOnClickListener {
            loginRequest()
            login.startAnimation()
        }


        return  view
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
            loginViewModel.loginUsersVM(userTaxId, userPassword)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }

}