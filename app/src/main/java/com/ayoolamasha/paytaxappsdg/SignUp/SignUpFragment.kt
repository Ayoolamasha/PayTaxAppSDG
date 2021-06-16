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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.STATUS
import com.ayoolamasha.paytaxappsdg.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var taxId: TextView
    private lateinit var copy: TextView
    private lateinit var login: TextView
    private lateinit var firstName: TextInputEditText
    private lateinit var lastName: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var createButton: CircularProgressButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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



        signUpViewModel.mLiveSignUpResponse.observe(requireActivity(), {
            run {
                lifecycleScope.launch{
                    when (it.STATUS) {
                        STATUS.SUCCESS -> {
                            createButton.dispose()
                            it.data?.let {
                                    response -> response as SignUpResponse
                                Toast.makeText(activity, "Successful", Toast.LENGTH_LONG).show()
                                Toast.makeText(activity, "response " +  response.signUpResponseData?.taxPayerId, Toast.LENGTH_SHORT).show()
                                withContext(Dispatchers.Main){
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
                                        response.signUpResponseData?.taxPayerId,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                dialogBuilder.setView(view1);
                                dialogBuilder.setCancelable(false);
                                dialog = dialogBuilder.create();
                                dialog.show()

                                taxId.text = response.signUpResponseData?.taxPayerId

                                copy.setOnClickListener {
                                    val taxCode: String = response.signUpResponseData?.taxPayerId?: return@setOnClickListener
                                    val clipData:ClipData = ClipData.newPlainText("text", taxCode)
                                    clipboardManager.setPrimaryClip(clipData)

                                    login.visibility=View.VISIBLE
                                }

                                login.setOnClickListener {
                                    view1?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)
                                    dialog.dismiss()
                                    Snackbar.make(view, "Login Credentials", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()

                                }


                                }

                            }

                        }
                        STATUS.LOADING -> {

                            Snackbar.make(view, "Error Signing", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()

                        }
                        STATUS.ERROR-> {
                            //Handle Error
                            Snackbar.make(view, "Error Signing", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()

                        }
                        else ->  Snackbar.make(view, "Check Your Network", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                    }

                }
            }

        })

        createButton.setOnClickListener {
            signUpRequest()
            createButton.startAnimation()

        }

        return view
    }


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

            signUpViewModel.signUpUsersVM(
                   userFirstName.trim(),
                   userLastName.trim(),
                   userEmail.trim(),
                   userPhone.trim(),
                   userPassword.trim()
               )

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }


}


