package com.samedtemiz.retrofit_kotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samedtemiz.retrofit_kotlin.R
import com.samedtemiz.retrofit_kotlin.adapter.RecyclerAdapter
import com.samedtemiz.retrofit_kotlin.databinding.ActivityMainBinding
import com.samedtemiz.retrofit_kotlin.model.CryptoModel
import com.samedtemiz.retrofit_kotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener {
    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerAdapter: RecyclerAdapter? = null

    //Disposible
    private var compositeDisposible: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposible = CompositeDisposable()

        //RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposible?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )


        /*
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {

                if(response.isSuccessful){
                    response.body()?.let{
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let{
                            recyclerAdapter = RecyclerAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = recyclerAdapter
                        }

                    }
                }

            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
         */
    }

    fun handleResponse(cryptoList : List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let{
            recyclerAdapter = RecyclerAdapter(it, this@MainActivity)
            binding.recyclerView.adapter = recyclerAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, cryptoModel.currency, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposible?.clear()
    }
}