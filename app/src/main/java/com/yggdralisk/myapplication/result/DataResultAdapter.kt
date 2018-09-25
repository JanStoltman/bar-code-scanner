package com.yggdralisk.myapplication.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yggdralisk.myapplication.R
import kotlinx.android.synthetic.main.data_recycler_row.view.*

class DataResultAdapter(val data: List<String>) : RecyclerView.Adapter<DataResultAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.data_recycler_row, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: String) {
            val label = data.split(':')[0]
            val text = data.split(':')[1]

            itemView.label.text = label.replace("/n","\n")
            itemView.text.text = text.replace("/n","\n")
        }
    }
}