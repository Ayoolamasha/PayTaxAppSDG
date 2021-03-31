package com.ayoolamasha.paytaxappsdg.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.R
import com.ayoolamasha.paytaxappsdg.Utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber


class SignUpFragment : Fragment() {

//    private val signUpViewModel:SignUpViewModel by lazy{
//        val activity = requireActivity()
//        ViewModelProvider(activity).get(SignUpViewModel::class.java)
//    }

    private lateinit var signUpViewModel:SignUpViewModel


    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var viewModel: SignUpViewModel

    private lateinit var firstName: TextInputEditText
    private lateinit var lastName: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var createButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpViewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application)).get(
            SignUpViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)


        init(view)

        view.findViewById<Button>(R.id.closeButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.backToLoginUp).setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        createButton.setOnClickListener {
            signUpRequest()
        }


        signUpViewModel.mLiveSignUpResponse.observe(requireActivity(), { type ->
            run {
                coroutineScope.launch {
                    when (type) {
                        is ApiResult.Success -> {
                            val success = type.response as SignUpResponse
                            Timber.tag("success").d(success.signUpResponseData?.taxPayerId)
                            Toast.makeText(activity, success.signUpResponseData?.taxPayerId, Toast.LENGTH_LONG).show()
                            //view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

        return view
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
//        // TODO: Use the ViewModel
//    }


    private fun init(view: View){
        firstName = view.findViewById(R.id.inputFirstName)
        lastName = view.findViewById(R.id.inputLastName)
        phone = view.findViewById(R.id.inputPhoneNumber)
        email = view.findViewById(R.id.inputEmail)
        password = view.findViewById(R.id.inputPassword)
        createButton = view.findViewById(R.id.createAccount)

    }

    private fun signUpRequest(){
        val userFirstName = firstName.text.toString().trim()
        val userLastName = lastName.text.toString().trim()
        val userPhone = phone.text.toString().trim()
        val userEmail = email.text.toString().trim()
        val userPassword = password.text.toString().trim()

        if (userFirstName.isEmpty() || userLastName.isEmpty() || userPassword.isEmpty()
            || userEmail.isEmpty() || userPhone.isEmpty()){

            Toast.makeText(activity, "Fill All Fields", Toast.LENGTH_SHORT).show()
        }else{

            signUpViewModel.userSignUp(
                userFirstName,
                userLastName,
                userEmail,
                userPhone,
                userPassword
            )

        }


    }

}