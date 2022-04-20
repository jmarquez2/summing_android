package com.jrms.summing.ui.spend

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jrms.summing.R
import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.repositories.SpendRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SpendViewModel(
    application: Application,
    private val spendRepository: SpendRepository,
) : AndroidViewModel(application) {

    private val limit = 20
    private var offset = 0
    private var loading = false
    private val spends : ArrayList<Spend> = ArrayList()

    init {
        getSpendList()
    }

    val spendListLiveData by lazy { MutableLiveData<ArrayList<Spend>>() }


    fun getSpendList(reset: Boolean = false, afterLoading: (() -> Unit)? = null) {
        if (!loading) {
            loading = true
            if (reset) {
                spends.clear()
                this.offset = 0
            }

            spendRepository.getSpendList(this.limit, this.offset)
                .enqueue(object : Callback<List<Spend>> {
                    override fun onResponse(
                        call: Call<List<Spend>>,
                        response: Response<List<Spend>>,
                    ) {
                        if (response.body() != null) {
                            val result: List<Spend> = response.body() as List<Spend>
                            if (result.count() > 0) {
                                spends.addAll(result)
                                spendListLiveData.postValue(spends)
                                this@SpendViewModel.offset += this@SpendViewModel.limit
                            }
                            afterLoading?.invoke()
                            loading = false
                        }
                    }

                    override fun onFailure(call: Call<List<Spend>>, t: Throwable) {
                        Toast.makeText(getApplication(), R.string.errorMessageWS,
                            Toast.LENGTH_SHORT).show()

                        afterLoading?.invoke()
                        loading = false
                    }

                })
        }

    }

    fun deleteSelection(spends: String, successCallback: () -> Unit, errorCallback: () -> Unit) {
        spendRepository.deleteSpends(spends).enqueue(object : Callback<ResponseWS> {
            override fun onResponse(call: Call<ResponseWS>, response: Response<ResponseWS>) {
                if (response.code() != 200) {
                    errorCallback()
                } else {
                    successCallback()
                }

            }

            override fun onFailure(call: Call<ResponseWS>, t: Throwable) {
                errorCallback()
            }

        })
    }
}