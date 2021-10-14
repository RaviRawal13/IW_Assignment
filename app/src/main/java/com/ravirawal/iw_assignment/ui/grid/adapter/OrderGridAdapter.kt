package com.ravirawal.iw_assignment.ui.grid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.ravirawal.iw_assignment.databinding.LayoutOrderItemGridBinding
import com.ravirawal.iw_assignment.databinding.LayoutOrderItemListBinding
import com.ravirawal.iw_assignment.model_ui.OrderedItem
import com.ravirawal.iw_assignment.model_ui.OrderedItemDiffCallBack
import com.ravirawal.iw_assignment.retrofit.RANDOM_IMAGE_URL
import com.ravirawal.iw_assignment.ui.list.adapter.OrderListAdapter
import com.ravirawal.iw_assignment.utils.gone
import com.ravirawal.iw_assignment.utils.loadImage
import com.ravirawal.iw_assignment.utils.visible

class OrderGridAdapter(private val onClick: (OrderedItem, Int) -> Unit = { _, _ -> run {} }) :
    RecyclerView.Adapter<OrderGridAdapter.OrderGridViewHolder>() {

    val differ = AsyncListDiffer(this, OrderedItemDiffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderGridViewHolder(
        LayoutOrderItemGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: OrderGridViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onClick)
    }


    inner class OrderGridViewHolder(private val binding: LayoutOrderItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderedItem: OrderedItem?, onClick: (OrderedItem, Int) -> Unit) {
            with(binding) {
                orderedItem?.let {
                    imageViewOrderItemGrid.loadImage("$RANDOM_IMAGE_URL${it.imageId}")
                    textViewTitleOrderItemGrid.text = it.name
                    textViewPriceOrderItemGrid.text = it.price
                }
            }
        }
    }
}