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

/**
 * for presenting all data
 *
 * @property fragment fragment of ShowdataFragment
 */
class DataAdapter(val fragment: ShowDataFragment): RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    var data = listOf<Data>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * getter for item count
     *
     * @return count of items
     */
    override fun getItemCount() = data.size

    /**
     * Bind data from database into item and add to holder
     * if you click on button edit, you go to edit it
     * if you click on button delete, you delete it from database
     *
     * @param holder view holder of the data
     * @param position position of the data in database
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.deleteButton.setOnClickListener {

            AlertDialog.Builder(fragment.requireContext())
                .setTitle(R.string.delete_data)
                .setCancelable(false)
                .setMessage(R.string.delete_data_message)
                .setPositiveButton("Cancel", null)
                .setNegativeButton("Delete") { _, _ -> fragment._viewModel.deleteData(item.dataId) }
                .show()

        }

        holder.updateButton.setOnClickListener {
            fragment.updateData(item.dataId)
        }
    }

    /**
     * Create view holder
     *
     * @param parent view which will contains view of item
     * @param viewType type of view
     * @return view of the item from parent
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * Class for working with layout data_list
     *
     * @param itemView item from database
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemAmount : TextView = itemView.findViewById(R.id.text_amount)
        val itemType : ImageView = itemView.findViewById(R.id.image_type)
        val itemCategory : TextView = itemView.findViewById(R.id.text_category)
        val itemDate : TextView = itemView.findViewById(R.id.text_date)
        val itemDescription : TextView = itemView.findViewById(R.id.text_description)
        val deleteButton : ImageButton = itemView.findViewById(R.id.delete_data)
        val updateButton : ImageButton = itemView.findViewById(R.id.edit_data)

        /**
         * into textviews in data_list layout set texts and set image of type
         *
         * @param item item from database
         */
        fun bind(item: Data) {
            val date = Instant.ofEpochMilli(item.date!!).atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            itemDate.text = date.format(format).toString()

            itemAmount.text = item.amount.toString()
            itemCategory.text = item.category

            if (item.description == null) {
                itemDescription.setText(R.string.no_description)
            } else {
                itemDescription.text = item.description
            }

            if (item.type == true) {
                itemType.setImageResource(R.drawable.ic_plus_48)
            } else {
                itemType.setImageResource(R.drawable.ic_minus_48)
            }
        }


        companion object {
            /**
             * adds view of item into parent view
             *
             * @param parent view which will contains view of item
             * @return view of the item
             */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.data_list, parent, false)

                return ViewHolder(view)
            }
        }
    }
}



