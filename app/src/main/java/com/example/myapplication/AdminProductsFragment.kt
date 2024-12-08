package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminProductsFragment : Fragment(R.layout.fragment_admin_products) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView'i tanımla
        recyclerView = view.findViewById(R.id.recyclerView) // XML dosyanızdaki RecyclerView'in ID'si
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(false)

        // Retrofit ile verileri çek
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com") // GitHub base URL'niz
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AdminProductAPI::class.java)
        service.getProducts().enqueue(object : Callback<List<ProductInfo>> {
            override fun onResponse(call: Call<List<ProductInfo>>, response: Response<List<ProductInfo>>) {
                if (response.isSuccessful && response.body() != null) {
                    val productList = response.body() ?: emptyList()
                    adapter = AddAdapter(productList)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Veriler alınamadı: Yanıt boş!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductInfo>>, t: Throwable) {
                Toast.makeText(requireContext(), "Hata: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}