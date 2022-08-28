package com.jrms.summing.ui.login

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.jrms.summing.BuildConfig
import com.jrms.summing.MainActivity
import com.jrms.summing.databinding.ActivityLoginBinding

import com.jrms.summing.R
import net.openid.appauth.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriGoogleLoginBuilder = Uri.parse(getString(R.string.urlGoogleAuth)).buildUpon().
        appendQueryParameter("client_id", BuildConfig.GOOGLE_CLIENT_ID).appendQueryParameter("scope", "email")
        .appendQueryParameter("redirect_uri", getString(R.string.uriRedirectSuccess))
        .appendQueryParameter("response_type", "code")
        .appendQueryParameter("access_type", "offline")
        .appendQueryParameter("prompt", "consent")

        val url = uriGoogleLoginBuilder.toString()*/

        val serviceConfiguration = AuthorizationServiceConfiguration(Uri.parse(getString(R.string.urlGoogleAuth)),
            Uri.parse(getString(R.string.urlGoogleToken))
        )

        val clientConfig = ClientSecretBasic(BuildConfig.GOOGLE_CLIENT_SECRET)


        val request = AuthorizationRequest.Builder(
            serviceConfiguration,
            BuildConfig.GOOGLE_CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse("${getString(R.string.schemeNameRedirect)}://${getString(R.string.hostRedirect)}")
        ).setScope("email")
            .build()

        val authorizationService = AuthorizationService(this)
        val intent = authorizationService.getAuthorizationRequestIntent(request)

        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { i  ->
            if(i.data != null){
                val response = AuthorizationResponse.fromIntent(i.data!!)
                val exception = AuthorizationException.fromIntent(i.data!!)

                val state = AuthState(response, exception)
                Log.d("Result", state.toString())
            }

        }

        result.launch(intent)


    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }*/




}

