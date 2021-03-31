package com.ayoolamasha.paytaxappsdg.Home

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.ayoolamasha.paytaxappsdg.R

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var selectTaxSpinner: Spinner
    private lateinit var taxAmountTextView: TextView
    private val selectTax : Array<String> = arrayOf("Stamp Duties",
        "Capital Gains Tax (individuals only)","Business Premises Levy",
        "Hotel Occupancy and restaurant consumption tax", "Withholding Taxes" )
    private  var spinnerDefault: String = "Personal Income Tax (PIT)"

    private lateinit var viewModel: HomeViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        init(view)

        selectTaxSpinner.adapter =
            activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, selectTax) }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun init(view:View){
        selectTaxSpinner = view.findViewById(R.id.taxesSpinner)
        taxAmountTextView = view.findViewById(R.id.taxesSpinner2)

    }


}