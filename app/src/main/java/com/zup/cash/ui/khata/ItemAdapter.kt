package com.zup.cash.ui.khata

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zup.cash.R
import com.zup.merchant.data.model.khata.Data


class ItemAdapter(private val mCtx: Context) : PagedListAdapter<Data, ItemAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.khata_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override  fun onBindViewHolder(@NonNull holder: ItemViewHolder, position: Int) {
        val data = getItem(position)

//        holder.customerName.text = data!!.payerDetails.firstName
        holder.dateTime.text = data!!.createdAt
        holder.amount.text = data.amount

//
//        khataViewHolder.amount.text = "+ ₹" + transactions?.get(position)?.creditAmount
//        khataViewHolder.dateTime.text = dateFormatter(transactions?.get(position)?.transactionDateTime!!)
//        khataViewHolder.transactionId.text = transactions?.get(position)?.transactionId
//        when (transactions?.get(position)?.status) {
//            "SUCCESS" -> {
//                khataViewHolder.paymentStatus.text = transactions?.get(position)?.status
//                khataViewHolder.paymentStatus.setTextColor(Color.parseColor("#009933"))
//            }
//            "INITIATED" -> {
//                khataViewHolder.paymentStatus.text = transactions?.get(position)?.status
//                khataViewHolder.paymentStatus.setTextColor(Color.parseColor("#003d99"))
//            }
//            "FAILED" -> {
//                khataViewHolder.paymentStatus.text = transactions?.get(position)?.status
//                khataViewHolder.paymentStatus.setTextColor(Color.parseColor("#b32400"))
//            }
//            "PENDING" -> {
//                khataViewHolder.paymentStatus.text = transactions?.get(position)?.status
//                khataViewHolder.paymentStatus.setTextColor(Color.parseColor("#FFFFFF"))
//            }
//
//        }
//        holder.userName.setText(image.getUser().toString())
//        holder.like.setText(image.getLikes().toString())
//        holder.comments.setText(image.getComments().toString())
//
//        Picasso.get()
//            .load(image.getLargeImageURL())
//            .placeholder(R.drawable.shimmer_background)
//            .into(holder.image)
//
//        val transformation = RoundedTransformationBuilder()
//            .borderColor(Color.BLACK)
//            .borderWidthDp(1)
//            .cornerRadiusDp(30)
//            .oval(false)
//            .build()
//
//        if (!image.getUserImageURL().isEmpty()) {
//            Picasso.get()
//                .load(image.getUserImageURL())
//                .fit()
//                .transform(transformation)
//                .into(holder.userImage)
//        }


    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var customerName: TextView
        var amount: TextView
        var dateTime: TextView
        var paymentStatus: TextView
        var transactionId: TextView

        init {
            customerName = view.findViewById(R.id.customer_name_tv)
            amount = view.findViewById(R.id.amount_tv)
            dateTime = view.findViewById(R.id.datetime_tv)
            paymentStatus = view.findViewById(R.id.payment_status)
            transactionId = view.findViewById(R.id.transactionId)
            view.setOnClickListener {
//                clickListener.onitemClick(view, adapterPosition)
            }

        }


    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.transactionId === newItem.transactionId
            }

            override  fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}


