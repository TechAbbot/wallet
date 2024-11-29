package com.android.wallet.ui.flexiblecontrol

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.android.wallet.R
import com.android.wallet.databinding.ActivityFlexibleControlsBinding
import com.android.wallet.model.cardandtransaction.CardAndTransactionResponse
import com.android.wallet.network.Status
import com.android.wallet.repository.virtualcard.VirtualCardRepository
import com.android.wallet.ui.base.BaseActivity
import com.android.wallet.ui.flexiblecontrol.adapter.FlexibleControlAdapter
import com.android.wallet.ui.transactionlist.TransactionListActivity
import com.android.wallet.ui.virtualcard.viewmodel.VirtualCardViewModel
import com.android.wallet.ui.virtualcard.viewmodel.VirtualCardViewModelFactory

class FlexibleControlsActivity : BaseActivity<ActivityFlexibleControlsBinding>() {
    private lateinit var viewModel: VirtualCardViewModel
    private lateinit var repository: VirtualCardRepository

    override fun getBinding(inflater: LayoutInflater): ActivityFlexibleControlsBinding {
        return ActivityFlexibleControlsBinding.inflate(inflater)
    }

    override fun onDoneKeyboardClicked(binding: ActivityFlexibleControlsBinding) {
        
    }

    override fun setupToolbar(binding: ActivityFlexibleControlsBinding) {
        binding.apply {
            includeToolbar.apply {
                ivToolbarBack.isVisible = true
                ivToolbarBack.setOnClickListener {
                    finish()
                }
                tvToolbarTitle.isVisible = true
                tvToolbarTitle.text = getString(R.string.flexible_controls)
            }
        }
    }

    override fun liveDataObservers(binding: ActivityFlexibleControlsBinding) {
        viewModel.cardAndTransaction.observe(this) {
            when (it.localStatus) {
                Status.SUCCESS -> {
                    val response = it.response as List<CardAndTransactionResponse>?

                    if (response != null) virtualCardList(response)
                }

                else -> {}
            }
        }
    }

    override fun onClickListeners(binding: ActivityFlexibleControlsBinding) {

    }

    override fun initViews(binding: ActivityFlexibleControlsBinding) {
        repository = VirtualCardRepository(this)
        viewModel = ViewModelProvider(
            this, VirtualCardViewModelFactory(repository)
        )[VirtualCardViewModel::class.java]
        viewModel.getCardAndTransactionApi<List<CardAndTransactionResponse?>>()
    }

    private fun virtualCardList(response: List<CardAndTransactionResponse?>) {
        binding.apply {

            val adapter = FlexibleControlAdapter(response) { selectedData ->
                startActivity(
                    Intent(
                        this@FlexibleControlsActivity, TransactionListActivity::class.java
                    ).putExtra("cardId", selectedData.cardId).setAction(
                        "your.custom.action"
                    )
                )
            }
            rvFlexibleControlCardList.adapter = adapter
        }
    }
}