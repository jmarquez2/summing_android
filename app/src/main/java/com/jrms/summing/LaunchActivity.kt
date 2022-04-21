package com.jrms.summing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jrms.summing.repositories.SessionRepository
import com.jrms.summing.repositories.SharedPreferencesRepository
import com.jrms.summing.ui.login.LoginActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LaunchActivity : AppCompatActivity() {

    private val sharedPreferencesRepository : SharedPreferencesRepository by inject()
    private val sessionRepository : SessionRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        var intent : Intent
        if(sharedPreferencesRepository.getToken() == null ){
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }else{
            intent = Intent(this, MainActivity::class.java)
            lifecycleScope.launch{
                try {
                    val result = sessionRepository.login()
                    Log.w("Result", result.message ?: "Empty")
                }catch (e : Exception){
                    Log.e("loginError", e.toString())
                    intent = Intent(this@LaunchActivity, LoginActivity::class.java)

                }finally {
                    startActivity(intent)
                    finish()
                }
            }

        }

    }
}