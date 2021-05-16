package com.jrms.summing.ui.home

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jrms.summing.R
import com.jrms.summing.models.Spend
import com.jrms.summing.repositories.SpendRepository
import com.jrms.summing.repositories.WebServiceRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(application: Application,
                    private val spendRepository: SpendRepository) : AndroidViewModel(application) {

    lateinit var openAddSpend : () -> Unit

    val spendListLiveData by lazy {MutableLiveData<List<Spend>>()}


    fun clickFabAdd(view : View){
        openAddSpend()
    }

    fun getSpendList() {
        spendRepository.getSpendList().enqueue(object : Callback<List<Spend>>{
            override fun onResponse(call: Call<List<Spend>>, response: Response<List<Spend>>) {
                if (response.body() != null){
                    spendListLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Spend>>, t: Throwable) {
                Toast.makeText(getApplication(), R.string.errorMessageWS, Toast.LENGTH_SHORT).show()
            }

        })
    }
}