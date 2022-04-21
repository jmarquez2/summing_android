package com.jrms.summing.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.jrms.summing.repositories.SessionRepository

import com.jrms.summing.R
import com.jrms.summing.models.Token
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class LoginViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {

        viewModelScope.launch {
            try {
                val token = sessionRepository.login(username, password)
                sessionRepository.setToken(token.token!!)
                _loginResult.value = LoginResult(LoggedInUserView(displayName = username), null)
            }catch (httpException : HttpException){
                Log.e("Login", "Error logging in",httpException)
                _loginResult.value = LoginResult(null, R.string.user_password_incorrect)

            }catch (e : Exception){
                Log.e("Login", "Error logging in",e)
                _loginResult.value = LoginResult(null, R.string.login_failed)
            }
        }

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