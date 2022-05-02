package com.example.financnykontrolor.fragments.showDataFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financnykontrolor.R
import com.example.financnykontrolor.database.Data
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DataAdapter(val view : ShowDataViewModel, val context : Context): RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    var data = listOf<Data>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.deleteButton.setOnClickListener() {

            val popUpWindow = AlertDialog.Builder(context)
            popUpWindow.setTitle(R.string.delete_data)
                .setCancelable(false)
                .setMessage(R.string.delete_data_message)
                .setPositiveButton("Cancel", null)
                .setNegativeButton("Delete") { _, id -> view.deleteData(item.dataId) }
                .show()

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemAmount : TextView = itemView.findViewById(R.id.text_amount)
        val itemType : ImageView = itemView.findViewById(R.id.image_type)
        val itemCategory : TextView = itemView.findViewById(R.id.text_category)
        val itemDate : TextView = itemView.findViewById(R.id.text_date)
        val itemDescription : TextView = itemView.findViewById(R.id.text_description)
        val deleteButton : ImageButton = itemView.findViewById(R.id.delete_data)

        fun bind(item: Data) {
            if (item.date != null) {
                val date = Instant.ofEpochMilli(item.date!!).atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                val format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                itemDate.text = date.format(format).toString()
            } else {
                itemDate.setText(R.string.no_date)
            }
            itemAmount.text = item.amount.toString()
            itemCategory.text = item.category
            itemDescription.text = item.description
            if (item.type == true) {
                itemType.setImageResource(R.drawable.ic_plus_48)
            } else {
                itemType.setImageResource(R.drawable.ic_minus_48)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.data_list, parent, false)

                return ViewHolder(view)
            }
        }
    }
}



