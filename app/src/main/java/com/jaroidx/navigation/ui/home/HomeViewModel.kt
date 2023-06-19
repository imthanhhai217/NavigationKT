package com.jaroidx.navigation.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jaroidx.navigation.api.`object`.BaseResponse
import com.jaroidx.navigation.models.ListProductResponse
import com.jaroidx.navigation.repositories.ListProductRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val listProductRepository: ListProductRepository, application: Application
) : AndroidViewModel(application) {

    val listProductResult: MutableLiveData<BaseResponse<ListProductResponse>> = MutableLiveData()
    var listProductResponse: ListProductResponse? = null
    var isLoading:MutableLiveData<Boolean> = MutableLiveData()

    fun getListProduct() {
        viewModelScope.launch {
            isLoading.postValue(true)
            listProductResult.postValue(BaseResponse.Loading())
            var result = listProductRepository.getListProduct()
            listProductResult.postValue(handlerListProductResponse(result))
        }
    }

    private fun handlerListProductResponse(response: Response<ListProductResponse>): BaseResponse<ListProductResponse> {
        isLoading.postValue(false)
        if (response.isSuccessful && response.code() == 200) {
            response.body()?.let {
                listProductResponse = it
                return BaseResponse.Success(listProductResponse)
            }
        }
        return BaseResponse.Error(response.body(), response.message())
    }
}