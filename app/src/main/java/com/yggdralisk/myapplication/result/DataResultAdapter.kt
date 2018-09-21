package com.yggdralisk.myapplication.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yggdralisk.myapplication.R
import com.yggdralisk.myapplication.data.model.DataModel
import kotlinx.android.synthetic.main.data_recycler_row.view.*

class DataResultAdapter(val data: Array<DataModel>) : RecyclerView.Adapter<DataResultAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.data_recycler_row, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataModel: DataModel) {
            itemView.data.text = dataModel.data[0]
            itemView.idv.text = dataModel.data[1]
            itemView.name.text = dataModel.data[2]
            itemView.netto.text = dataModel.data[3]
            itemView.brutto.text = dataModel.data[4]
            itemView.state.text = dataModel.data[5]
            itemView.jm.text = dataModel.data[6]
            itemView.vat.text = dataModel.data[7]
        }
    }
}