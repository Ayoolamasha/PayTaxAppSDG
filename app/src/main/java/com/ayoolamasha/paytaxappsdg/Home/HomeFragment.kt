package com.ayoolamasha.paytaxappsdg.Home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.ayoolamasha.paytaxappsdg.Login.LoginViewModel
import com.ayoolamasha.paytaxappsdg.R
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataRoomDatabase
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private val homeViewModel: HomeViewModel by viewModels()


    private lateinit var incomeTextView: TextInputEditText
    private lateinit var selectTaxSpinner: Spinner
    private lateinit var taxAmountTextView: TextView
    private val selectTax : Array<String> = arrayOf("Stamp Duties",
        "Capital Gains Tax (individuals only)","Business Premises Levy",
        "Hotel Occupancy and restaurant consumption tax", "Withholding Taxes" )
    private  var spinnerDefault: String = "Personal Income Tax (PIT)"
    private var accessTokenFig: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MTgwNDkxNjcsImV4cCI6MTYxODEzNTU2N30.XF3N1s8jg7UiXM0EfIb_VMZt0eJbk4c5CWCu71v9AmA"

    private lateinit var dynamicName:TextView
    private lateinit var dynamicTaxId:TextView
    private lateinit var dynamicTaxPayable:TextView

    private lateinit var calculateTaxButton: CircularProgressButton
    private lateinit var makePayment: Button
    private lateinit var progressBar: ProgressBar

    lateinit var fullName:String

    lateinit var taxID: String
    lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeViewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application))
//            .get(HomeViewModel::class.java)


        fullName = arguments?.getString("name").toString()
        //accessToken = arguments?.getString("accessToken").toString()
        taxID = arguments?.getString("taxId").toString()
        userEmail = arguments?.getString("userEmail").toString()

    }



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        init(view)


//        homeViewModel.viewModelScope.launch {
//            homeViewModel.getAllLiveUserPojo().collect{
//                dynamicName.text =
//            }
//        }






//        Toast.makeText(context, "User Name is:  "+ homeViewModel.getUserTaxPayerId(accessTokenss).toString()
//            , Toast.LENGTH_LONG).show()
        dynamicName.text = fullName
        dynamicTaxId.text = taxID

//        selectTaxSpinner.setOnItemClickListener(){
//            homeViewModel.getTaxTypeViewModel()
//        }
//
        selectTaxSpinner.adapter =
            activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, selectTax) }



//        homeViewModel.mLiveGetTaxTypeResponse.observe(requireActivity(), {type ->
//            run {
//                coroutineScope.launch {
//                    when(type){
//                        is ApiResult.Success ->{
//                            val success = type.response as CalculateTaxResponse
//                            val taxPay:String = success.data
//
//                            withContext(Dispatchers.Main){
//                                calculateTaxButton.dispose()
//                                dynamicTaxPayable.text = taxPay
//                                makePayment.visibility = View.VISIBLE
//
//
//                            }
//
////
////                            coroutineScope.launch(Dispatchers.Main) {
////                                calculateTaxButton.dispose()
////                                dynamicTaxPayable.text = taxPay
////                                makePayment.visibility = View.VISIBLE
////
////                            }
//
//                        }
//                        is ApiResult.NetworkError -> {
//                            Snackbar.make(
//                                view,
//                                "Check your Network Connection",
//                                Snackbar.LENGTH_SHORT
//                            ).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
//                        }
//                        is ApiResult.Failure -> {
//                            val error: CalculateTaxResponse = type.response as CalculateTaxResponse
//                            Timber.tag("Failure").d(error.message)
//                            Snackbar.make(
//                                view,
//                                error.message,
//                                Snackbar.LENGTH_SHORT
//                            ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
//                        }
//                        is ApiResult.Exception -> {
//                            Timber.tag("Exception").d(type.t)
//                            type.t.message?.let {
//                                Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
//                                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
//                            }
//                        }
//                        else -> ApiResult.NetworkError(true)
//                    }
//
//                }
//            }
//        })

        calculateTaxButton.setOnClickListener {
            //calculateIncome()
            calculateTaxButton.startAnimation()
        }

        makePayment.setOnClickListener {
            val payable:String = dynamicTaxPayable.text.toString()
            val bundle = bundleOf(
                "amount" to payable,
                "userEmail" to userEmail,
                "fullName" to fullName,
                "tax_type" to "IRS"
            )

            view.findNavController().navigate(R.id.action_homeFragment_to_makePayment, bundle)
        }




        return view
    }

    private fun init(view:View){
        selectTaxSpinner = view.findViewById(R.id.taxesSpinner)
        dynamicName = view.findViewById(R.id.dynamicName)
        dynamicTaxId = view.findViewById(R.id.dynamicTaxID)
        incomeTextView = view.findViewById(R.id.dynamicIncome)
        calculateTaxButton = view.findViewById(R.id.calaulateTax)
        dynamicTaxPayable = view.findViewById(R.id.dynamicPayableAmount)
        makePayment = view.findViewById(R.id.makePaymentButton)
        progressBar = view.findViewById(R.id.calculateLoading)

    }
//
//    private fun calculateIncome(){
//        val taxId = taxID
//        val income = incomeTextView.text.toString().trim()
//
//        if (income.isEmpty()){
//            Toast.makeText(activity, "Input Income" , Toast.LENGTH_SHORT).show()
//            calculateTaxButton.dispose()
//            progressBar.visibility = View.INVISIBLE
//        }else{
//            homeViewModel.makeCalculationsViewModel(taxId, income)
//        }
//    }


}