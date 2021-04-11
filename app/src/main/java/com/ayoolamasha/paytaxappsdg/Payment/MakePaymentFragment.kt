package com.ayoolamasha.paytaxappsdg.Payment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.Home.HomeViewModel
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxResponse
import com.ayoolamasha.paytaxappsdg.Models.PaymentResponse
import com.ayoolamasha.paytaxappsdg.R
import com.ayoolamasha.paytaxappsdg.Utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import timber.log.Timber

class MakePaymentFragment : Fragment() {

    companion object {
        fun newInstance() = MakePaymentFragment()
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private lateinit var viewModel: MakePaymentViewModel

    private lateinit var checkBox: CheckBox
    private lateinit var payButton: CircularProgressButton

    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    lateinit var amountPayable:String
    lateinit var userEmail:String
    lateinit var fullName: String
    lateinit var taxType: String

    private lateinit var reference:TextView
    private lateinit var back: Button
    private lateinit var authUrl:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application))
            .get(MakePaymentViewModel::class.java)


        fullName = arguments?.getString("fullName").toString()
        amountPayable = arguments?.getString("amount").toString()
        taxType = arguments?.getString("tax_type").toString()
        userEmail = arguments?.getString("userEmail").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.make_payment_fragment, container, false)
        init(view)

        viewModel.mLiveMakePaymentResponse.observe(requireActivity(), {type ->
            run {
                coroutineScope.launch {
                    when(type){
                        is ApiResult.Success ->{
                            val success = type.response as PaymentResponse
                            val authorizationUrl:String = success.paymentresponsecompositeTwo.authorization_url
                            val referencePay:String = success.paymentresponsecompositeTwo.reference

                            withContext(Dispatchers.Main){
                                dialogBuilder = AlertDialog.Builder(activity)

                                val view1: View? = layoutInflater.inflate(
                                    R.layout.layout_payment_inflater,
                                    container,
                                    false
                                )

                                if (view1 != null) {
                                    reference = view1.findViewById(R.id.dynamicReference)
                                    back = view1.findViewById(R.id.dynamicUrl)
                                    authUrl = view1.findViewById(R.id.backButton)

                                }else{
                                    Toast.makeText(
                                        activity,
                                        "Error",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                dialogBuilder.setView(view1);
                                dialogBuilder.setCancelable(false);
                                dialog = dialogBuilder.create();
                                dialog.show()

                                reference.text= success.paymentresponsecompositeTwo.reference
                                authUrl.text = success.paymentresponsecompositeTwo.authorization_url

                                back.setOnClickListener {
                                    view1?.findNavController()?.navigate(R.id.action_makePayment_to_homeFragment)
                                    dialog.dismiss()
                                }

                            }


                        }
                        is ApiResult.NetworkError -> {
                            Snackbar.make(
                                view,
                                "Check your Network Connection",
                                Snackbar.LENGTH_SHORT
                            ).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                        }
                        is ApiResult.Failure -> {
                            val error: CalculateTaxResponse = type.response as CalculateTaxResponse
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


        payButton.setOnClickListener {
            makePayment()
            payButton.startAnimation()
        }

        return view
    }

    private fun init(view: View){
        checkBox = view.findViewById(R.id.checkbox)
        payButton = view.findViewById(R.id.PaymentButton)
    }

    private fun makePayment(){
        if (amountPayable.isEmpty() || fullName.isEmpty() || userEmail.isEmpty() || taxType.isEmpty()){
            Toast.makeText(activity, "Credientials not complete", Toast.LENGTH_SHORT).show()
            payButton.dispose()
        }else{
            viewModel.makePaymentViewModel(amountPayable, userEmail, fullName, taxType)
        }
    }



}