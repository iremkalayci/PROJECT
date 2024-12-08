package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductsFragment : Fragment(), ProductRecyclerAdapter.Listener {


    private var productRecyclerAdapter : ProductRecyclerAdapter? = null

    //Shared ViewModel
    private val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity?.baseContext,2)

        productViewModel.downloadData()
        productViewModel.productList.observe(viewLifecycleOwner, Observer {
            productRecyclerAdapter = ProductRecyclerAdapter(it,this)
            recyclerView.adapter = productRecyclerAdapter
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onItemClick(product: Product) {
        productViewModel.addToBasket(product)

        productViewModel.basket.observe(viewLifecycleOwner, Observer {
            it.forEach {
                println(it.name)
            }
        })
    }


}