package com.example.myapplication
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler

import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel : ViewModel() {

    val productList = MutableLiveData<List<Product>>()
    val basket = MutableLiveData<List<Product>>()
    val totalBasket = MutableLiveData<Int>()
    var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }


    fun downloadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)

        job = viewModelScope.launch(context = Dispatchers.IO + exceptionHandler) {
            val response = retrofit.getData()


            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    response.body()?.let {
                        productList.value = it
                    }
                }
            }

        }

    }

    fun addToBasket(product : Product) {
        if(basket.value != null) {
            val arrayList = ArrayList(basket.value)
            if (arrayList.contains(product)) {
                val indexOfFirst = arrayList.indexOfFirst { it == product }
                val relatedProduct = arrayList[indexOfFirst]
                relatedProduct.count += 1
                basket.value = arrayList
            } else {
                product.count += 1
                arrayList.add(product)
                basket.value = arrayList
            }
        } else {
            val arrayList = arrayListOf(product)
            product.count += 1
            basket.value = arrayList
        }


        basket.value.let {
            refreshTotalValue(it!!)
        }
    }

    private fun refreshTotalValue(listOfProducts: List<Product>) {
        var total = 0
        listOfProducts.forEach { product ->
            val price = product.price.toIntOrNull()
            price?.let {
                val count = product.count
                val revenue = count * it
                total += revenue
            }
        }
        totalBasket.value = total
    }

    fun deleteProductFromBasket(product : Product) {
        if (basket.value != null) {
            val arrayList = ArrayList(basket.value)
            arrayList.remove(product)
            basket.value = arrayList
            refreshTotalValue(arrayList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}