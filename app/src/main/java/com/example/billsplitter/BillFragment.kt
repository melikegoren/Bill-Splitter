package com.example.billsplitter

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.billsplitter.databinding.FragmentBillBinding




class BillFragment : Fragment(R.layout.fragment_bill) {
    private var binding: FragmentBillBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentBillBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            lifecycleOwner = viewLifecycleOwner
            billFragment = this@BillFragment
        }

        binding!!.editTxtTotalAmount.addTextChangedListener( loginTextWatcher)
        binding!!.editTxtTotalNumOfPeop.addTextChangedListener( loginTextWatcher)
        binding!!.editTxtTipAmount.addTextChangedListener( loginTextWatcher)

    }

    private val loginTextWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            val totalAmount = binding!!.editTxtTotalAmount.text.toString().trim()
            val numOfPeople = binding!!.editTxtTotalNumOfPeop.text.toString().trim()
            val tipAmount = binding!!.editTxtTipAmount.text.toString().toString()
            binding!!.button.isEnabled = totalAmount.isNotEmpty() && numOfPeople.isNotEmpty() && tipAmount.isNotEmpty()

        }

    }

    fun calculate(): Double{
        var billAmount: String?
        var numberOfPeople: String?
        var tipAmount: String?

            billAmount = binding!!.editTxtTotalAmount.text.toString()
            numberOfPeople = binding!!.editTxtTotalNumOfPeop.text.toString()
            tipAmount = binding!!.editTxtTipAmount.text.toString()



        val result = (billAmount.toDouble()+((billAmount.toDouble().times(tipAmount.toInt()))/100))/numberOfPeople.toInt()
        val result2 = String.format("%.2f", result)
        return result2.toDouble()

    }


    fun onCreateDialog(): Dialog {
        val result = calculate()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Amount Per Person")
                .setMessage("$"+result.toString())
                .setCancelable(true)
                .setNegativeButton("Reset",
                DialogInterface.OnClickListener{ _,  _ ->
                    reset()
                })
                .show()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun reset(){
        binding!!.editTxtTotalAmount.text = null
        binding!!.editTxtTotalNumOfPeop.text = null
        binding!!.editTxtTipAmount.text = null


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}





