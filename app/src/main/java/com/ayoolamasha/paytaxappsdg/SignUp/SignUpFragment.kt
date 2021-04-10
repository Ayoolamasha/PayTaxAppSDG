package com.ayoolamasha.paytaxappsdg.SignUp

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
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

    private lateinit var signUpViewModel:SignUpViewModel

    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    private lateinit var clipboardManager: ClipboardManager
    //private lateinit var clipData: ClipData

    private lateinit var taxId: TextView
    private lateinit var copy: TextView
    private lateinit var login: TextView


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
    private lateinit var createButton: CircularProgressButton



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
            createButton.startAnimation()

        }


        signUpViewModel.mLiveSignUpResponse.observe(requireActivity(), { type ->
            run {
                coroutineScope.launch {
                    when (type) {
                        is ApiResult.Success -> {
                            createButton.dispose()
                            val success = type.response as SignUpResponse
                            Timber.tag("success").d(success.signUpResponseData?.taxPayerId)
                            Timber.tag("success").d(success.accessToken)
                            //Timber.tag("success").d(success.status)

                            coroutineScope.launch(Dispatchers.Main) {
                                dialogBuilder = AlertDialog.Builder(activity)

                                val view1: View? = layoutInflater.inflate(
                                    R.layout.layout_sign_up_dialog,
                                    container,
                                    false
                                )

                                if (view1 != null) {
                                    taxId = view1.findViewById(R.id.dynamicPayerID)
                                    copy = view1.findViewById(R.id.copyStatic)
                                    login = view1.findViewById(R.id.navToLogin)

                                }else{
                                    Toast.makeText(
                                        activity,
                                        success.signUpResponseData?.taxPayerId,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                dialogBuilder.setView(view1);
                                dialogBuilder.setCancelable(false);
                                dialog = dialogBuilder.create();
                                dialog.show()

                                taxId.text = success.signUpResponseData?.taxPayerId

                                copy.setOnClickListener {
                                    val taxCode: String = success.signUpResponseData?.taxPayerId?: return@setOnClickListener
                                    val clipData:ClipData = ClipData.newPlainText("text", taxCode)
                                    clipboardManager.setPrimaryClip(clipData)

                                    login.visibility=View.VISIBLE
                                }

                                login.setOnClickListener {
                                    view1?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)
                                    dialog.dismiss()

                                }



                            }
                            //view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        is ApiResult.NetworkError -> {
                            createButton.dispose()
                            Snackbar.make(
                                view,
                                "Check your Network Connection",
                                Snackbar.LENGTH_SHORT
                            ).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                        }
                        is ApiResult.Failure -> {
                            createButton.dispose()
                            val error: SignUpErrorPojo = type as SignUpErrorPojo
                            Timber.tag("Failure").d(error.message)
                            Snackbar.make(
                                view,
                                error.message!!,
                                Snackbar.LENGTH_SHORT
                            ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
                        }
                        is ApiResult.Exception -> {
                            createButton.dispose()
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
        clipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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