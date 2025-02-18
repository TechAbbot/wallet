package com.android.wallet.ui.virtualcard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wallet.network.ApiState
import com.android.wallet.repository.virtualcard.VirtualCardRepository
import com.android.wallet.ui.base.BaseViewModel
import com.android.wallet.model.transactionlist.request.TransactionListRequest
import kotlinx.coroutines.launch

class VirtualCardViewModel(
    val repository: VirtualCardRepository
) : BaseViewModel() {
    val _cardAndTransactionResponse: MutableLiveData<ApiState<*>> = MutableLiveData()
    val cardAndTransaction: LiveData<ApiState<*>>
        get() {
            return _cardAndTransactionResponse
        }

    inline fun <reified T> getCardAndTransactionApi() {
        viewModelScope.launch {
            val response = repository.getCardAndTransactionApi<T>()
            launch {
                _cardAndTransactionResponse.postValue(response)
            }
        }
    }

    inline fun <reified T> getLastNTransactionApi(transactionListRequest: TransactionListRequest) {
        viewModelScope.launch {
            val response = repository.getLastNTransactionApi<T>(transactionListRequest)
            launch {
                _cardAndTransactionResponse.postValue(response)
            }
        }
    }
}