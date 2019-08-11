package com.example.logintest.ui.dashBoard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.logintest.R
import com.example.logintest.constants.AppConstants.Companion.PASSED_DATA
import com.example.logintest.data.model.LoggedInUser
import com.example.logintest.databinding.ActivitySuccessBinding
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.login.LoginActivity
import com.example.logintest.ui.order.OrderActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_success.*


class LoginSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySuccessBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_success
        )
        val model: LoggedInUser = intent.getParcelableExtra(PASSED_DATA)
        binding.user = model
        binding.eventListener = object : EventListeners.LogoutEvents {
            override fun showOrders() {
                val i = Intent(applicationContext, OrderActivity::class.java)
                startActivity(i)
            }

            override fun onLogout() {
                //Declared activity as singleTask in manifest
                val i = Intent(applicationContext, LoginActivity::class.java)
                startActivity(i)
            }

        }
        val welcome = getString(R.string.welcome)
        val snackbar = Snackbar
            .make(successContainer, "$welcome ${model.displayName}", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onBackPressed() {
    }
}