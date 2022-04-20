package com.jrms.summing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jrms.summing.models.Spend
import com.jrms.summing.other.SPEND_LIST_EXTRA
import com.jrms.summing.repositories.SharedPreferencesRepository
import com.jrms.summing.repositories.SpendRepository
import com.jrms.summing.ui.login.LoginActivity
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaunchActivity : AppCompatActivity() {

    private val sharedPreferencesRepository : SharedPreferencesRepository by inject()
    private val spendRepository : SpendRepository by inject()

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
            spendRepository.getSpendList().enqueue(object : Callback<List<Spend>>{
                override fun onResponse(call: Call<List<Spend>>, response: Response<List<Spend>>) {
                    if(response.code() != 200){
                        intent = Intent(this@LaunchActivity, LoginActivity::class.java)
                    }else{
                        intent.putExtra(SPEND_LIST_EXTRA, ArrayList(response.body()!!))
                    }

                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<List<Spend>>, t: Throwable) {
                    startActivity(intent)
                    finish()
                }

            })
        }

    }
}