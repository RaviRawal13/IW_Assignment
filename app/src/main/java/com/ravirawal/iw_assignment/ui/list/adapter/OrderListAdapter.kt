package com.ravirawal.iw_assignment.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.ravirawal.iw_assignment.databinding.LayoutOrderItemListBinding
import com.ravirawal.iw_assignment.model_ui.OrderedItem
import com.ravirawal.iw_assignment.model_ui.OrderedItemDiffCallBack
import com.ravirawal.iw_assignment.retrofit.RANDOM_IMAGE_URL
import com.ravirawal.iw_assignment.utils.gone
import com.ravirawal.iw_assignment.utils.loadImage
import com.ravirawal.iw_assignment.utils.visible

class OrderListAdapter(private val onClick: (OrderedItem, Int) -> Unit = { _, _ -> run {} }) :
    RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {

    private val differ = AsyncListDiffer(this, OrderedItemDiffCallBack)

    fun submitList(list: List<OrderedItem>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderListViewHolder(
        LayoutOrderItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onClick)
    }

    inner class OrderListViewHolder(private val binding: LayoutOrderItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderedItem: OrderedItem?, onClick: (OrderedItem, Int) -> Unit) {
            with(binding) {
                orderedItem?.let {
                    imageViewOrderItemList.loadImage("$RANDOM_IMAGE_URL${it.imageId}")
                    textViewTitleOrderItemList.text = it.name
                    textViewPriceOrderItemList.text = it.price
                    if (it.extra?.isNotBlank() == true) {
                        textViewExtraOrderItemList.text = it.extra
                        textViewExtraOrderItemList.visible()
                    } else {
                        textViewExtraOrderItemList.gone()
                    }
                }
            }
        }
    }
}