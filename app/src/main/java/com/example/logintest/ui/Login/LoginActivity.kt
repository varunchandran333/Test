package com.example.logintest.ui.Login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.logintest.R
import com.example.logintest.constants.AppConstants.Companion.PASSED_DATA
import com.example.logintest.constants.AppConstants.Companion.SAVED_PASSWORD
import com.example.logintest.constants.AppConstants.Companion.SAVED_USERNAME
import com.example.logintest.data.model.LoggedInUser
import com.example.logintest.data.model.UserDetails
import com.example.logintest.databinding.ActivityMainBinding
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.Order.LoginSuccess
import com.example.logintest.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        //get shared preference saved data
        getData(binding)
        loginViewModel = ViewModelProviders.of(this, ViewModelFactory())
            .get(LoginViewModel::class.java)
        loginViewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer
            // disable login button and remember button unless both username / password is valid
            binding.isValid = loginState.isDataValid
            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            } else
                binding.username.error = null
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            } else
                binding.password.error = null
        })

        loginViewModel.loginResult.observe(this, Observer {
            val loginResult = it ?: return@Observer
            binding.visibility = false
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)
        })
        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            binding.username.text.toString(),
                            binding.password.text.toString()
                        )
                }
                false
            }
        }
        binding.username.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }
        }
        binding.eventListener = object : EventListeners.LoginEvents {
            override fun onLogin() {
                binding.visibility = true
                loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
            }

            override fun onRemember() {
                val sharedPref = this@LoginActivity.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putString(SAVED_USERNAME, binding.username.text.toString())
                    putString(SAVED_PASSWORD, binding.password.text.toString())
                    apply()
                }
                val snackbar = Snackbar
                    .make(container, getString(R.string.credentialSaved), Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUser) {
        val i = Intent(this, LoginSuccess::class.java)
        i.putExtra(PASSED_DATA, model)
        startActivity(i)
        finish()
    }

    private fun getData(binding: ActivityMainBinding) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val username = sharedPref.getString(SAVED_USERNAME, "")
        val password = sharedPref.getString(SAVED_PASSWORD, "")
        binding.user = UserDetails(username = username, password = password)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
