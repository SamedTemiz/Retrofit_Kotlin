package com.samedtemiz.retrofit_kotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samedtemiz.retrofit_kotlin.R
import com.samedtemiz.retrofit_kotlin.model.CryptoModel

class RecyclerAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerAdapter.RowHolder>() {

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private var colors: Array<String> = arrayOf("#F67280", "#C06C84", "#6C5B7B", "#355C7D", "#213363", "#17594A", "#8EAC50", "#D3D04F")
    class RowHolder(view : View) : RecyclerView.ViewHolder(view){

        val txtCurrency: TextView
        val txtPrice: TextView

        init{
            txtCurrency = view.findViewById(R.id.txtCurrency)
            txtPrice = view.findViewById(R.id.txtPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)

        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.txtCurrency.text = cryptoList.get(position).currency
        holder.txtPrice.text = cryptoList.get(position).price
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % colors.size]))
        holder.itemView.setOnClickListener{
            listener.onItemClick(cryptoList.get(position))
        }
    }
}