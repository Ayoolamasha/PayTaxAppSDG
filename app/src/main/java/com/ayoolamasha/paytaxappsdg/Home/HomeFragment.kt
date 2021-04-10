package com.ayoolamasha.paytaxappsdg.Home

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.ayoolamasha.paytaxappsdg.ApiCallBacks.ApiResult
import com.ayoolamasha.paytaxappsdg.BaseApplication
import com.ayoolamasha.paytaxappsdg.Models.CalculateTaxResponse
import com.ayoolamasha.paytaxappsdg.Models.GetDataTypesResponse
import com.ayoolamasha.paytaxappsdg.R
import com.ayoolamasha.paytaxappsdg.SignUp.SignUpErrorPojo
import com.ayoolamasha.paytaxappsdg.UserData.UserDataPojo
import com.ayoolamasha.paytaxappsdg.Utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import timber.log.Timber

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    lateinit var userDataPojo: UserDataPojo

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    private lateinit var homeViewModel: HomeViewModel

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
    lateinit var accessToken:String
    lateinit var taxID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application))
            .get(HomeViewModel::class.java)


        fullName = arguments?.getString("name").toString()
        accessToken = arguments?.getString("accessToken").toString()
        taxID = arguments?.getString("taxId").toString()
    }



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        init(view)


        Toast.makeText(context, "User Name is $fullName", Toast.LENGTH_LONG).show()
        dynamicName.text = fullName
        dynamicTaxId.text = taxID

//        selectTaxSpinner.setOnItemClickListener(){
//            homeViewModel.getTaxTypeViewModel()
//        }
//
        selectTaxSpinner.adapter =
            activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, selectTax) }



        homeViewModel.mLiveGetTaxTypeResponse.observe(requireActivity(), {type ->
            run {
                coroutineScope.launch {
                    when(type){
                        is ApiResult.Success ->{
                            val success = type.response as CalculateTaxResponse
                            val taxPay:String = success.data


                            coroutineScope.launch(Dispatchers.Main) {
                                calculateTaxButton.dispose()
                                dynamicTaxPayable.text = taxPay
                                makePayment.visibility = View.VISIBLE

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

        calculateTaxButton.setOnClickListener {
            calculateIncome()
            calculateTaxButton.startAnimation()
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

    fun calculateIncome(){
        val taxId = taxID
        val income = incomeTextView.text.toString().trim()

        if (income.isEmpty()){
            Toast.makeText(activity, "Input Income" , Toast.LENGTH_SHORT).show()
            calculateTaxButton.dispose()
            progressBar.visibility = View.INVISIBLE
        }else{
            homeViewModel.makeCalculationsViewModel(taxId, income)
        }
    }


}