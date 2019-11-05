package com.zup.cash.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.zup.cash.R
import com.zup.cash.di.component.ActivityComponent
import com.zup.cash.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : BaseActivity<MainViewModel>(), PaymentResultListener {

    companion object{
        const val TAG = "MainActivity"//use for logging in this activity
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /**
     * initialize view/objects in setupView method
     */
    override fun setupView(savedInstanceState: Bundle?) {
        et_phone.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {viewModel.onPhoneChange(p0.toString())}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO check if phone number is valid using Regex
                //TODO add unit test case for the Regex method
                //TODO Enable the button
            }

        })

        bt_login.setOnClickListener { viewModel.login() }
        //Checkout.preload(this)
        //startPayment()
    }

    override fun setupObservers(){
        super.setupObservers()
        viewModel.phoneField.observe(this, Observer {
            if(et_phone.text.toString()!=it) et_phone.setText(it)
        })

        viewModel.loggingIn.observe(this, Observer {
            pb_loading.visibility = if(it) View.VISIBLE else View.GONE
        })
    }

    override fun onPaymentError(p0: Int, p1: String?) {
    }

    override fun onPaymentSuccess(p0: String?) {
    }

    private fun startPayment(){
        try {
            val checkout = Checkout()

            val data = JSONObject()
            data.put("amount", 1000) // pass in currency subunits. For example, paise. Amount: 1000 equals ₹10
            data.put("email", "somecustomer@somesite.com")
            data.put("currency", "INR")
            data.put("contact", "8800931460")
            val notes = JSONObject()
            notes.put("custom_field", "abc");
            data.put("notes", notes);
            data.put("method", "netbanking");
            // Method specific fields
            data.put("bank", "HDFC");
            checkout.open(this,data)

        }
        catch (ex : Exception){

        }
    }
}


