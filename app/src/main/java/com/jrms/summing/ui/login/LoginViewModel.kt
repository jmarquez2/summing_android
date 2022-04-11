package com.jrms.summing.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.jrms.summing.repositories.SessionRepository

import com.jrms.summing.R
import com.jrms.summing.models.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {

        sessionRepository.login(username, password).enqueue(object : Callback<Token>{
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                when {
                    response.code() == 200 -> {
                        sessionRepository.setToken(response.body()!!.token!!)
                        _loginResult.value = LoginResult(LoggedInUserView(displayName = username), null)
                    }
                    response.code() == 404 -> {
                        _loginResult.value = LoginResult(null, R.string.user_not_found)
                    }
                    else -> {
                        _loginResult.value = LoginResult(null, R.string.incorrect_password)
                    }
                }

            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                _loginResult.value = LoginResult(null, R.string.login_failed)
            }

        })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}