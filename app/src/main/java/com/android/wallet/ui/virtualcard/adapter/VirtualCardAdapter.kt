package com.android.wallet.ui.virtualcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.wallet.R
import com.android.wallet.databinding.ItemVirtualCardsBinding
import com.android.wallet.model.cardandtransaction.CardAndTransactionResponse
import com.android.wallet.utils.AppUtils.convertCardNumberToStar
import java.text.NumberFormat
import java.util.Locale

class VirtualCardAdapter(
    private val cardList: List<CardAndTransactionResponse?>,
    private val callback: (CardAndTransactionResponse) -> Unit
) : RecyclerView.Adapter<VirtualCardAdapter.SelectCustomerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCustomerViewHolder {
        val binding = ItemVirtualCardsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SelectCustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectCustomerViewHolder, position: Int) {
        val item = cardList[position]
        if (item != null) holder.bind(item, position)
    }

    override fun getItemCount() = cardList.size

    inner class SelectCustomerViewHolder(private val binding: ItemVirtualCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CardAndTransactionResponse, position: Int) {
            binding.apply {
                try {
                    tvItemVirtualCardAmount.text = root.context.getString(
                        R.string.amount_with_symbol,
                        NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(item.availableAmount.toString().toDouble())
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    tvItemVirtualCardAmount.text = root.context.getString(
                        R.string.amount_with_symbol, "~~"
                    )
                }

                tvItemVirtualCardAccountNumber.text = item.cardNumber?.convertCardNumberToStar()
                tvItemVirtualCardBankName.text = item.bankName
                root.setOnClickListener {
                    callback.invoke(item)
                }
            }
        }
    }
}