package com.example.paymenthistory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paymenthistory.R
import com.example.domain.model.Payment
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Adapter for displaying a list of payments in a RecyclerView.
 *
 * @property paymentList List of payments to be displayed in the adapter.
 */
class PaymentRecyclerAdapter(private val paymentList: MutableList<Payment?>) :
    RecyclerView.Adapter<PaymentRecyclerAdapter.PaymentViewHolder>() {

    /**
     * ViewHolder for an individual payment list item.
     *
     * @property title TextView for displaying the payment title.
     * @property timestamp TextView for displaying the payment timestamp.
     * @property amount TextView for displaying the payment amount.
     */
    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.item_id)
        val title: TextView = itemView.findViewById(R.id.title)
        val timestamp: TextView = itemView.findViewById(R.id.description)
        val amount: TextView = itemView.findViewById(R.id.amount)
    }

    /**
     * Creates and returns a new instance of [PaymentViewHolder].
     *
     * @param parent The parent ViewGroup in which the new ViewHolder will be created.
     * @param viewType The type of the new ViewHolder's view.
     * @return A new instance of [PaymentViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment, parent, false)
        return PaymentViewHolder(itemView)
    }

    /**
     * Updates the contents of the ViewHolder based on the data at the given position.
     *
     * @param holder The ViewHolder to be updated.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val currentItem = paymentList[position]

        // Check for null and remove the item if it's null
        if (paymentList[position] == null) {
            paymentList.remove(paymentList[position])
            notify()
            return
        }

        // Format and set the payment id
        holder.id.text = String.format(
            holder.amount.context.getString(R.string.payments_id_title),
            currentItem!!.id.toString()
        )

        // Format and set the payment title
        val correctTitle: Boolean = currentItem.title != null && currentItem.title != ""
        holder.title.text = if (correctTitle) currentItem.title else holder.title.context.getString(R.string.no_info)

        // Format and set the payment timestamp
        val sdf = SimpleDateFormat(holder.timestamp.context.getString(R.string.timestamp_format))
        sdf.timeZone = TimeZone.getDefault()
        val correctTimestamp: Boolean = currentItem.created != null && currentItem.created != ""
        holder.timestamp.text = if (correctTimestamp) sdf.format(
            Date(currentItem.created!!.toLong() * 1000L)).toString()
        else holder.timestamp.context.getString(R.string.no_info)

        // Format and set the payment amount
        val correctAmount: Boolean = currentItem.amount != null && currentItem.amount != ""
        holder.amount.text = String.format(
            holder.amount.context.getString(R.string.amount_format),
            if (correctAmount) currentItem.amount else holder.amount.context.getString(R.string.no_info),
            if (correctAmount) holder.amount.context.getString(R.string.currency_eur) else ""
        )
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    override fun getItemCount() = paymentList.size
}
