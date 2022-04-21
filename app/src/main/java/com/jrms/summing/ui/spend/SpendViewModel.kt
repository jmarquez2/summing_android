package com.jrms.summing.ui.spend

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jrms.summing.R
import com.jrms.summing.models.ResponseWS
import com.jrms.summing.models.Spend
import com.jrms.summing.repositories.SpendRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class SpendViewModel(
    application: Application,
    private val spendRepository: SpendRepository,
) : AndroidViewModel(application) {

    private val limit = 20
    private var offset = 0
    private val spends: ArrayList<Spend> = ArrayList()


    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val spendListLiveData by lazy { MutableLiveData<ArrayList<Spend>>() }


    private var isLoading = false

    val loadingLiveData: LiveData<Boolean>
        get() {
            return _loading
        }

    init {
        getSpendList()
    }

    private fun loading(value: Boolean) {
        isLoading = value
        _loading.postValue(isLoading)

    }

    fun getSpendList(reset: Boolean = false) {
        if (!isLoading) {
            loading(true)
            if (reset) {
                spends.clear()
                this.offset = 0
            }

            viewModelScope.launch(Dispatchers.IO) {

                try {
                    val result: List<Spend> = spendRepository.getSpendList(limit, offset)
                    if (result.count() > 0) {
                        spends.addAll(result)
                        spendListLiveData.postValue(spends)
                        this@SpendViewModel.offset += this@SpendViewModel.limit
                    }
                } catch (e: Exception) {
                    Toast.makeText(getApplication(), R.string.errorMessageWS,
                        Toast.LENGTH_SHORT).show()
                } finally {
                    loading(false)
                }

            }

        }

    }

    suspend fun deleteSelections(
        spends: String
    ) : Boolean {

        try {
            val result = spendRepository.deleteSpends(spends)
            Log.w("Delete selection", result.message ?: "Empty message")
            return true
        } catch (e: Exception) {
            Log.e("Delete selection", "Error", e)
            return false
        }
    }
}